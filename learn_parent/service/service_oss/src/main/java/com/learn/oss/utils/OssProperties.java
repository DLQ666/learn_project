package com.learn.oss.utils;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @program: learn_parent
 * @description: 从配置文件读取  常量属性读取工具类--->第二种方法
 * @author: Hasee
 * @create: 2020-06-20 15:55
 */
@Data
@Component
@ConfigurationProperties(prefix = "aliyun.oss")
public class OssProperties {
    private String endpoint;
    private String keyid;
    private String keysecret;
    private String bucketname;
}
