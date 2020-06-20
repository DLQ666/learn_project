package com.learn.oss.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

/**
 * @program: learn_parent
 * @description: Service层接口
 * @author: Hasee
 * @create: 2020-06-17 12:47
 */
public interface OssService {
    /**
     * 上传头像到oss--->第一种方式
     * @param file
     * @return 返回上传到oss的路径
     */
    /*String uploadFileAvater(MultipartFile file);*/

    /**
     * 阿里云oos文件上传--->第二种
     * @param inputStream 输入流
     * @param module 文件夹名称
     * @param originalFileName 原始文件名
     * @return 文件在oos服务器上的url地址
     */
    String upload(InputStream inputStream,String module,String originalFilename);
}
