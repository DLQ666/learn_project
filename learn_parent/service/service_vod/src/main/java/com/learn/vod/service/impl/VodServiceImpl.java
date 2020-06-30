package com.learn.vod.service.impl;

import com.aliyun.vod.upload.impl.UploadVideoImpl;
import com.aliyun.vod.upload.req.UploadStreamRequest;
import com.aliyun.vod.upload.resp.UploadStreamResponse;
import com.learn.service.base.exception.CustomException;
import com.learn.utils.result.ResultCodeEnum;
import com.learn.vod.service.VodService;
import com.learn.vod.util.VodProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.InputStream;

/**
 * @program: learn_parent
 * @description: Vod服务层接口实现
 * @author: Hasee
 * @create: 2020-06-30 20:35
 */
@Service
@Slf4j
public class VodServiceImpl implements VodService {

    @Autowired
    private VodProperties vodProperties;

    @Override
    public String uploadVideo(InputStream inputStream, String originalFilename) {

        String title = originalFilename.substring(0, originalFilename.lastIndexOf("."));

        UploadStreamRequest request = new UploadStreamRequest(vodProperties.getKeyid(), vodProperties.getKeysecret(), title, originalFilename, inputStream);

        /* 模板组ID(可选) */
        request.setTemplateGroupId(vodProperties.getTemplateGroupId());
        UploadVideoImpl uploader = new UploadVideoImpl();
        UploadStreamResponse response = uploader.uploadStream(request);

        String videoId = response.getVideoId();
        if (StringUtils.isEmpty(videoId)){
            log.error("阿里云上传失败："+ response.getCode() + "-" +response.getMessage());
            throw new CustomException(ResultCodeEnum.VIDEO_UPLOAD_ALIYUN_ERROR);
        }

        return videoId;
    }
}
