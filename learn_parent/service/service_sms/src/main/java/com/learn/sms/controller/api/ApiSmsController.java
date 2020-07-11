package com.learn.sms.controller.api;

import com.aliyuncs.exceptions.ClientException;
import com.learn.sms.service.SmsService;
import com.learn.utils.result.ResponseResult;
import com.learn.utils.utils.FormUtils;
import com.learn.utils.utils.RandomUtils;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

/**
 * @program: learn_parent
 * @description: 短信服务前端控制器
 * @author: Hasee
 * @create: 2020-07-04 15:16
 */
@Api(description = "短信管理")
@Slf4j
@RestController
@RequestMapping("/api/sms")
public class ApiSmsController {

    @Autowired
    private SmsService smsService;
    @Autowired
    private RedisTemplate redisTemplate;

    @GetMapping("/send/{mobile}")
    public ResponseResult getCode(@PathVariable("mobile") String mobile) throws ClientException {
        //校验手机号是否合法
        if (StringUtils.isEmpty(mobile) || !FormUtils.isMobile(mobile)){
            log.info("手机号不正确");
//            new CustomException(ResultCodeEnum.LOGIN_MOBILE_ERROR);
            return ResponseResult.error().message("手机号不正确").code(28001);
        }
        //生成验证码
        String checkCode = RandomUtils.getFourBitRandom();

        //发送验证码
        smsService.send(mobile,checkCode);

        //存储验证码到redis
        redisTemplate.opsForValue().set(mobile,checkCode,5, TimeUnit.MINUTES);
        return ResponseResult.ok().message("短信发送成功");
    }

}
