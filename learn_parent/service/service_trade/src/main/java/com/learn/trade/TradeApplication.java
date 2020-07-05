package com.learn.trade;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * @program: learn_parent
 * @description: 交易微服务启动类
 * @author: Hasee
 * @create: 2020-07-05 21:34
 */
@EnableFeignClients
@EnableDiscoveryClient
@ComponentScan({"com.learn"})
@SpringBootApplication
public class TradeApplication {
    public static void main(String[] args) {
        SpringApplication.run(TradeApplication.class,args);
    }
}
