package com.learn.vod;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

/**
 * @program: learn_parent
 * @description: 课程视频管理启动类
 * @author: Hasee
 * @create: 2020-06-30 16:29
 */
@EnableDiscoveryClient
@ComponentScan(basePackages = {"com.learn"})
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class VodApplication {
    public static void main(String[] args) {
        SpringApplication.run(VodApplication.class,args);
    }
}
