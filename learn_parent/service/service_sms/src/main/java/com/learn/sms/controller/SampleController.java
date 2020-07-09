package com.learn.sms.controller;

import com.learn.sms.util.SmsProperties;
import com.learn.utils.result.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: learn_parent
 * @description: Nacos配置中心实例
 * @author: Hasee
 * @create: 2020-07-09 15:52
 */
@RefreshScope
@RestController
@RequestMapping("/sms/sample")
public class SampleController {

    @Value("${aliyun.sms.signName}")
    private String signName;
    @Autowired
    private SmsProperties smsProperties;

    @GetMapping("/testSignName")
    public ResponseResult testSignName() {
        return ResponseResult.ok().data("signName", signName);
    }

    @GetMapping("/testSmsProperties")
    public ResponseResult testSmsProperties() {
        return ResponseResult.ok().data("smsProperties", smsProperties);
    }
}
