package com.learn.vod.util;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @program: learn_parent
 * @description: vod常量读取工具类
 * @author: Hasee
 * @create: 2020-06-30 20:25
 */
@Data
@Component
@ConfigurationProperties(prefix="aliyun.vod")
public class VodProperties {
    private String keyid;
    private String keysecret;
    private String templateGroupId;
    private String workflowId;
}
