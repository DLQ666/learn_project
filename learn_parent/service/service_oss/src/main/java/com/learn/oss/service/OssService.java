package com.learn.oss.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * @program: learn_parent
 * @description: Service层接口
 * @author: Hasee
 * @create: 2020-06-17 12:47
 */
public interface OssService {
    /**
     * 上传头像到oss
     * @param file
     * @return 返回上传到oss的路径
     */
    String uploadFileAvater(MultipartFile file);
}
