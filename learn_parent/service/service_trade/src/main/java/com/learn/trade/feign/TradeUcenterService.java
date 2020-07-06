package com.learn.trade.feign;

import com.learn.service.base.dto.MemberDto;
import com.learn.trade.feign.fallback.TradeUcenterServiceFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @description: 远程调用ucenter会员信息微服务接口
 * @author: Hasee
 * @create: 2020-07-06 13:56
 */
@Component
@FeignClient(value = "service-ucenter",fallback = TradeUcenterServiceFallback.class)
public interface TradeUcenterService {

    @GetMapping("/api/ucenter/member/inner/getMemberDto/{memberId}")
    public MemberDto getMemberDtoByMemberId(@PathVariable("memberId") String memberId);
}
