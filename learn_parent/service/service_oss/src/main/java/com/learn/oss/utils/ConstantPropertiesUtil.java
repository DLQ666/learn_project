package com.learn.oss.utils;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @program: learn_parent
 * @description: 从配置文件读取  常量属性读取工具类--->第一种方法
 * @author: Hasee
 * @create: 2020-06-17 12:35
 * 因为配置属性为私有，外部不能使用。
 * 当项目启动，spring接口，spring加载之后，执行接口一个方法
 */
@Component
public class ConstantPropertiesUtil implements InitializingBean {

    /**
     * 读取配置文件内容
     */
    @Value("${aliyun.oss.file.endpoint}")
    private String endpoint;

    @Value("${aliyun.oss.file.keyid}")
    private String keyid;

    @Value("${aliyun.oss.file.keysecret}")
    private String keysecret;

    @Value("${aliyun.oss.file.bucketname}")
    private String bucketname;

    /**
     * 定义公开静态常量
     */
    public static String END_POINT;
    public static String KEY_ID;
    public static String KEY_SECRET;
    public static String BUCKET_NAME;

    @Override
    public void afterPropertiesSet() throws Exception {
        END_POINT=endpoint;
        KEY_ID=keyid;
        KEY_SECRET=keysecret;
        BUCKET_NAME=bucketname;
    }
}
