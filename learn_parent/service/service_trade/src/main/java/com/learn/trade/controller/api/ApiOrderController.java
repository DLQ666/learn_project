package com.learn.trade.controller.api;

import com.learn.trade.entity.Order;
import com.learn.trade.service.OrderService;
import com.learn.utils.result.ResponseResult;
import com.learn.utils.result.ResultCodeEnum;
import com.learn.utils.utils.JwtInfo;
import com.learn.utils.utils.JwtUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p>
 * 订单 前端控制器
 * </p>
 *
 * @author dlq
 * @since 2020-07-05
 */


@Api(description = "网站订单管理")
@Slf4j
@RestController
@RequestMapping("/api/trade/order")
public class ApiOrderController {

    @Autowired
    private OrderService orderService;

    @ApiOperation("新增订单")
    @PostMapping("/auth/save/{courseId}")
    public ResponseResult save(@PathVariable("courseId") String courseId, HttpServletRequest request) {

        JwtInfo jwtInfo = JwtUtils.getMemberIdByJwtToken(request);
        String orderId = orderService.saveOrder(courseId, jwtInfo.getId());
        return ResponseResult.ok().data("orderId", orderId);
    }

    @ApiOperation("获取订单")
    @GetMapping("/auth/get/{orderId}")
    public ResponseResult get(@PathVariable("orderId") String orderId, HttpServletRequest request) {
        JwtInfo jwtInfo = JwtUtils.getMemberIdByJwtToken(request);
        Order order = orderService.getByOrderId(orderId, jwtInfo.getId());
        return ResponseResult.ok().data("item", order);
    }

    @ApiOperation("判断课程是否购买")
    @GetMapping("/auth/isBuy/{courseId}")
    public ResponseResult isBuyByCourseId(@PathVariable("courseId") String courseId, HttpServletRequest request) {
        JwtInfo jwtInfo = JwtUtils.getMemberIdByJwtToken(request);
        Boolean isBuy = orderService.isBuyCourseId(courseId, jwtInfo.getId());
        return ResponseResult.ok().data("isBuy", isBuy);
    }

    @ApiOperation(value = "获取当前用户订单列表")
    @GetMapping("/auth/list")
    public ResponseResult list(HttpServletRequest request) {
        JwtInfo jwtInfo = JwtUtils.getMemberIdByJwtToken(request);
        List<Order> list = orderService.selectByMemberId(jwtInfo.getId());
        return ResponseResult.ok().data("items",list);
    }

    @ApiOperation(value = "删除订单")
    @DeleteMapping("/auth/remove/{orderId}")
    public ResponseResult remove(@PathVariable("orderId") String orderId, HttpServletRequest request) {
        JwtInfo jwtInfo = JwtUtils.getMemberIdByJwtToken(request);
        boolean result = orderService.removeById(orderId,jwtInfo.getId());
        if (result){
            return ResponseResult.ok().message("删除订单成功");
        }else {
            return ResponseResult.error().message("数据不存在");
        }
    }

    @GetMapping("/query-pay-status/{orderNo}")
    public ResponseResult queryPayStatus(@PathVariable String orderNo) {
        boolean result = orderService.queryPayStatus(orderNo);
        if (result) {//支付成功
            return ResponseResult.ok().message("支付成功");
        }
        return ResponseResult.setResult(ResultCodeEnum.PAY_RUN);//支付中
    }
}

