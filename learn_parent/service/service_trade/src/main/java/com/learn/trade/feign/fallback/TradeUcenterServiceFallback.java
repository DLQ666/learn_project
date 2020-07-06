package com.learn.trade.feign.fallback;

import com.learn.service.base.dto.MemberDto;
import com.learn.trade.feign.TradeUcenterService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @program: learn_parent
 * @description: 服务熔断保护类
 * @author: Hasee
 * @create: 2020-07-06 14:22
 */
@Slf4j
@Service
public class TradeUcenterServiceFallback implements TradeUcenterService {
    @Override
    public MemberDto getMemberDtoByMemberId(String memberId) {
        log.info("熔断保护");
        return null;
    }
}
