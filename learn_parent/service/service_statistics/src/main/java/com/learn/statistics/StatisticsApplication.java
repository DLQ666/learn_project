package com.learn.statistics;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @program: learn_parent
 * @description: 统计图表启动类
 * @author: Hasee
 * @create: 2020-07-08 19:43
 */
@EnableScheduling
@EnableFeignClients
@EnableDiscoveryClient
@ComponentScan({"com.learn"})
@SpringBootApplication
public class StatisticsApplication {
    public static void main(String[] args) {
        SpringApplication.run(StatisticsApplication.class,args);
    }
}
