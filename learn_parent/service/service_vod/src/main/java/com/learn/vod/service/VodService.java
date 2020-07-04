package com.learn.vod.service;

import com.aliyuncs.exceptions.ClientException;

import java.io.InputStream;
import java.util.List;

/**
 * @description: Vod服务层接口
 * @author: Hasee
 * @create: 2020-06-30 20:32
 */
public interface VodService {

    String uploadVideo(InputStream inputStream, String originalFilename);

    public void removeVideo(String videoId) throws ClientException;

    void removeVideoByIdList(List<String> videoIdList) throws ClientException;

    String getPlayAuth(String videoSourceId) throws ClientException;
}
