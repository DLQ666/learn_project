package com.learn.statistics.feign.fallback;

import com.learn.statistics.feign.MemberService;
import com.learn.utils.result.ResponseResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @program: learn_parent
 * @description: 远程调用熔断器
 * @author: Hasee
 * @create: 2020-07-08 19:50
 */
@Slf4j
@Service
public class MemberServiceFallback implements MemberService {

    @Override
    public ResponseResult countRegisterNum(String day) {
        log.error("熔断被执行");
        return ResponseResult.ok().data("registerNum",0);
    }
}
