package com.learn.user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

/**
 * @program: learn_parent
 * @description: 用户中心启动类
 * @author: Hasee
 * @create: 2020-07-04 17:05
 */
@ComponentScan({"com.learn"})
@EnableDiscoveryClient
@SpringBootApplication
public class UcenterApplication {
    public static void main(String[] args) {
        SpringApplication.run(UcenterApplication.class,args);
    }
}
