package com.learn.trade.service.impl;

import com.github.wxpay.sdk.WXPayUtil;
import com.learn.service.base.exception.CustomException;
import com.learn.trade.entity.Order;
import com.learn.trade.service.OrderService;
import com.learn.trade.service.WeixinPayService;
import com.learn.trade.util.WeixinPayProperties;
import com.learn.utils.result.ResultCodeEnum;
import com.learn.utils.utils.ExceptionUtil;
import com.learn.utils.utils.HttpClientUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @program: learn_parent
 * @description: 微信支付业务实现类
 * @author: Hasee
 * @create: 2020-07-07 15:10
 */
@Slf4j
@Service
public class WeixinPayServiceImpl implements WeixinPayService {

    @Autowired
    private OrderService orderService;
    @Autowired
    private WeixinPayProperties weixinPayProperties;

    @Override
    public Map<String, Object> createNative(String orderNo, String remoteAddr) {

        try {
            //根据订单号获取订单
            Order order = orderService.getOrderByOrderNo(orderNo);

            //调用微信api统一下单接口
            HttpClientUtils client = new HttpClientUtils("https://api.mch.weixin.qq.com/pay/unifiedorder");

            //组装参数
            Map<String, String> params = new HashMap<>();
            //公众账号ID
            params.put("appid",weixinPayProperties.getAppId());
            //商户号
            params.put("mch_id",weixinPayProperties.getPartner());
            //随机字符串 长度要求在32位以内。推荐随机数生成算法
            params.put("nonce_str", WXPayUtil.generateNonceStr());
            //商品描述
            params.put("body", order.getCourseTitle());
            //订单号
            params.put("out_trade_no", orderNo);
            //标价金额(单位：分)
            params.put("total_fee", order.getTotalFee().intValue()+"");
            //终端ip
            params.put("spbill_create_ip", remoteAddr);
            //通知地址(回调地址)
            params.put("notify_url", weixinPayProperties.getNotifyUrl());
            //交易类型
            params.put("trade_type", "NATIVE");

            //组装sign签名  将参数转换为xml字符串，并且在字符串的最后追加计算的签名
            String xmlParams = WXPayUtil.generateSignedXml(params, weixinPayProperties.getPartnerKey());
            log.info("\n xmlParams \n"+xmlParams);

            //将参数放入请求对象方法体
            client.setXmlParam(xmlParams);
            //使用https形式发送
            client.setHttps(true);
            //使用post方式发送请求
            client.post();
            //得到响应
            String resultXml = client.getContent();
            log.info("\n resultXml: \n "+resultXml);

            Map<String, String> resultMap = WXPayUtil.xmlToMap(resultXml);

            //错误处理
            if("FAIL".equals(resultMap.get("return_code")) || "FAIL".equals(resultMap.get("result_code"))){
                log.error("微信支付统一下单错误 - "
                        + "return_code: " + resultMap.get("return_code")
                        + "return_msg: " + resultMap.get("return_msg")
                        + "result_code: " + resultMap.get("result_code")
                        + "err_code: " + resultMap.get("err_code")
                        + "err_code_des: " + resultMap.get("err_code_des"));

                throw new CustomException(ResultCodeEnum.PAY_UNIFIEDORDER_ERROR);
            }

            Map<String, Object> map = new HashMap<>();
            //交易标识
            map.put("return_code",resultMap.get("return_code"));
            //二维码url
            map.put("code_url",resultMap.get("code_url"));
            map.put("course_id",order.getCourseId());
            map.put("total_fee",order.getTotalFee());
            map.put("out_trade_no",orderNo);

            return map;
        } catch (Exception e) {
            log.error(ExceptionUtil.getMessage(e));
            throw new CustomException(ResultCodeEnum.PAY_UNIFIEDORDER_ERROR);
        }
    }

}
