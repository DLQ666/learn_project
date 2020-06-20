package com.learn.oss;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

/**
 * @program: learn_parent
 * @description: OSS启动类
 * @author: Hasee
 * @create: 2020-06-17 12:26
 * exclude = DataSourceAutoConfiguration.class ：取消数据源自动配置
 */
@ComponentScan(basePackages = {"com.learn"})
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class OssApplication {
    public static void main(String[] args) {
        SpringApplication.run(OssApplication.class,args);
    }
}
