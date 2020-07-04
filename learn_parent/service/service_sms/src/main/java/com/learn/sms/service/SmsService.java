package com.learn.sms.service;

import com.aliyuncs.exceptions.ClientException;
import org.springframework.stereotype.Service;

/**
 * @program: learn_parent
 * @description: 短信服务service层
 * @author: Hasee
 * @create: 2020-07-04 15:17
 */
public interface SmsService {
    void send(String mobile, String checkCode) throws ClientException;
}
