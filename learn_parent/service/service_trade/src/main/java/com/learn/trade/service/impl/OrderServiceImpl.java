package com.learn.trade.service.impl;

import com.learn.trade.entity.Order;
import com.learn.trade.mapper.OrderMapper;
import com.learn.trade.service.OrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 订单 服务实现类
 * </p>
 *
 * @author dlq
 * @since 2020-07-05
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {

}
