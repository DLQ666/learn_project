package com.learn.trade.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.gson.Gson;
import com.learn.service.base.dto.CourseDto;
import com.learn.service.base.dto.MemberDto;
import com.learn.service.base.exception.CustomException;
import com.learn.trade.entity.Order;
import com.learn.trade.entity.PayLog;
import com.learn.trade.feign.EduCourseService;
import com.learn.trade.feign.TradeUcenterService;
import com.learn.trade.mapper.OrderMapper;
import com.learn.trade.mapper.PayLogMapper;
import com.learn.trade.service.OrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.learn.trade.util.OrderNoUtils;
import com.learn.utils.result.ResultCodeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 订单 服务实现类
 * </p>
 *
 * @author dlq
 * @since 2020-07-05
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {

    @Autowired
    private EduCourseService eduCourseService;
    @Autowired
    private TradeUcenterService tradeUcenterService;
    @Autowired
    private PayLogMapper payLogMapper;

    @Override
    public String saveOrder(String courseId, String memberId) {

        //查询当前用户是否已有当前课程的订单
        QueryWrapper<Order> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("course_id", courseId);
        queryWrapper.eq("member_id", memberId);
        Order orderExist = baseMapper.selectOne(queryWrapper);
        //判断是否存在订单
        if (orderExist != null) {
            //如果订单已存在，则返回订单id
            return orderExist.getId();
        }

        //查询课程基本信息
        CourseDto courseDto = eduCourseService.getCourseDtoById(courseId);
        if (courseDto == null) {
            throw new CustomException(ResultCodeEnum.PARAM_ERROR);
        }
        //查询用户基本信息
        MemberDto memberDto = tradeUcenterService.getMemberDtoByMemberId(memberId);
        if (memberDto == null) {
            throw new CustomException(ResultCodeEnum.PARAM_ERROR);
        }

        //创建订单
        Order order = new Order();
        //生成订单号
        order.setOrderNo(OrderNoUtils.getOrderNo());
        order.setCourseId(courseId);
        order.setCourseTitle(courseDto.getTitle());
        order.setCourseCover(courseDto.getCover());
        order.setTeacherName(courseDto.getTeacherName());
        //单位：分 ---> 换算成元
        order.setTotalFee(courseDto.getPrice().multiply(new BigDecimal(100)));

        order.setMemberId(memberId);
        order.setMobile(memberDto.getMobile());
        order.setNickname(memberDto.getNickname());

        //未支付
        order.setStatus(0);
        //微信支付
        order.setPayType(1);

        baseMapper.insert(order);
        return order.getId();
    }

    @Override
    public Order getByOrderId(String orderId, String memberId) {
        QueryWrapper<Order> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", orderId);
        queryWrapper.eq("member_id", memberId);
        return baseMapper.selectOne(queryWrapper);
    }

    @Override
    public Boolean isBuyCourseId(String courseId, String memberId) {
        QueryWrapper<Order> queryWrapper = new QueryWrapper<>();
        queryWrapper
                .eq("course_id", courseId)
                .eq("member_id", memberId)
                .eq("status", 1);

        Integer count = baseMapper.selectCount(queryWrapper);
        return count.intValue() > 0;
    }

    @Override
    public List<Order> selectByMemberId(String memberId) {
        QueryWrapper<Order> queryWrapper = new QueryWrapper<>();
        queryWrapper
                .orderByDesc("gmt_create")
                .eq("member_id",memberId);
        return baseMapper.selectList(queryWrapper);
    }

    @Override
    public boolean removeById(String orderId, String memberId) {
        QueryWrapper<Order> queryWrapper = new QueryWrapper<>();
        queryWrapper
                .eq("id",orderId)
                .eq("member_id",memberId);
        return this.remove(queryWrapper);

    }

    @Override
    public Order getOrderByOrderNo(String orderNo) {
        QueryWrapper<Order> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("order_no",orderNo);
        return baseMapper.selectOne(queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateOrderStatus(Map<String, String> notifyMap) {
        //更新订单状态
        String outTradeNo = notifyMap.get("out_trade_no");
        Order order = this.getOrderByOrderNo(outTradeNo);
        //支付成功
        order.setStatus(1);
        baseMapper.updateById(order);

        //记录支付日志
        PayLog payLog = new PayLog();
        payLog.setOrderNo(outTradeNo);
        payLog.setPayTime(new Date());
        //支付类型：微信支付  支付类型（1：微信 2：支付宝）
        payLog.setPayType(1);
        payLog.setTotalFee(Long.parseLong(notifyMap.get("total_fee")));
        payLog.setTradeState(notifyMap.get("result_code"));
        payLog.setTransactionId(notifyMap.get("transaction_id"));
        payLog.setAttr(new Gson().toJson(notifyMap));
        payLogMapper.insert(payLog);

        //更新课程销量
        eduCourseService.updateBuyCountById(order.getCourseId());
    }

    /**
     * 查询支付结果
     * @param orderNo 订单号
     * @return true：已支付  false：未支付
     */
    @Override
    public boolean queryPayStatus(String orderNo) {
        QueryWrapper<Order> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("order_no",orderNo);
        Order order = baseMapper.selectOne(queryWrapper);
        return order.getStatus()==1;

    }
}
