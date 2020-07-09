package com.learn.statistics.feign;

import com.learn.statistics.feign.fallback.MemberServiceFallback;
import com.learn.utils.result.ResponseResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @description: 远程调用用户中心统计注册数接口
 * @author: Hasee
 * @create: 2020-07-08 19:49
 */
@Component
@FeignClient(value = "service-ucenter",fallback = MemberServiceFallback.class)
public interface MemberService {

    @GetMapping("/admin/ucenter/member/countRegisterNum/{day}")
    public ResponseResult countRegisterNum(@PathVariable("day") String day);
}
