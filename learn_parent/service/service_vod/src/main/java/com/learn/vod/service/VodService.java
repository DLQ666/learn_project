package com.learn.vod.service;

import java.io.InputStream;

/**
 * @description: Vod服务层接口
 * @author: Hasee
 * @create: 2020-06-30 20:32
 */
public interface VodService {

    String uploadVideo(InputStream inputStream, String originalFilename);
}
