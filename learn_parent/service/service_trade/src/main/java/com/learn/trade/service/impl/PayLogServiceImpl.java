package com.learn.trade.service.impl;

import com.learn.trade.entity.PayLog;
import com.learn.trade.mapper.PayLogMapper;
import com.learn.trade.service.PayLogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 支付日志表 服务实现类
 * </p>
 *
 * @author dlq
 * @since 2020-07-05
 */
@Service
public class PayLogServiceImpl extends ServiceImpl<PayLogMapper, PayLog> implements PayLogService {

}
