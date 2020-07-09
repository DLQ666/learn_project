package com.learn.trade.service;


import java.util.Map;

/**
 * @description: 微信支付接口
 * @author: Hasee
 * @create: 2020-07-07 15:03
 */
public interface WeixinPayService {

    Map<String, Object> createNative(String orderNo, String remoteAddr);
}
