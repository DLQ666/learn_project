package com.learn.cms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * @program: learn_parent
 * @description: cms启动类
 * @author: Hasee
 * @create: 2020-07-03 15:51
 */
@EnableFeignClients
@EnableDiscoveryClient
@ComponentScan({"com.learn"})
@SpringBootApplication
public class CmsApplication {
    public static void main(String[] args) {
        SpringApplication.run(CmsApplication.class,args);
    }
}
