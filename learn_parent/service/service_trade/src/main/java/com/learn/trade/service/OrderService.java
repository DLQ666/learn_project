package com.learn.trade.service;

import com.learn.trade.entity.Order;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 订单 服务类
 * </p>
 *
 * @author dlq
 * @since 2020-07-05
 */
public interface OrderService extends IService<Order> {

    /**
     * 创建订单
     * @param courseId 课程id
     * @param memberId 会员id
     * @return 订单号
     */
    String saveOrder(String courseId, String memberId);

    /**
     * 获取订单页面信息--->回显
     * @param orderId 订单id
     * @param memberId 用户id
     * @return 订单信息
     */
    Order getByOrderId(String orderId, String memberId);

    /**
     * 判断课程是否购买
     * @param courseId 课程id
     * @param memberId 会员id
     * @return 布尔
     */
    Boolean isBuyCourseId(String courseId, String memberId);

    /**
     * 获取当前用户订单列表
     * @param memberId 用户id
     * @return 订单列表
     */
    List<Order> selectByMemberId(String memberId);

    /**
     * 根据订单id和会员id删除订单
     * @param orderId 订单id
     * @param memberId 会员id
     * @return 布尔
     */
    boolean removeById(String orderId, String memberId);

    /**
     * 根据订单号查询订单
     * @param orderNo 订单号
     * @return 订单信息
     */
    Order getOrderByOrderNo(String orderNo);

    /**
     * 回调接口中收到微信发来的支付成功通知，更改订单表的支付状态
     * @param notifyMap
     */
    void updateOrderStatus(Map<String, String> notifyMap);

    /**
     * 查询订单状态---轮询（每3秒查询一次）
     * @param orderNo 订单号
     * @return 布尔
     */
    boolean queryPayStatus(String orderNo);

}
