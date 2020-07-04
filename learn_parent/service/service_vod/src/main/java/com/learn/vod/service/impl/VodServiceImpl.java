package com.learn.vod.service.impl;

import com.aliyun.vod.upload.impl.UploadVideoImpl;
import com.aliyun.vod.upload.req.UploadStreamRequest;
import com.aliyun.vod.upload.resp.UploadStreamResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.vod.model.v20170321.DeleteVideoRequest;
import com.aliyuncs.vod.model.v20170321.DeleteVideoResponse;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthResponse;
import com.learn.service.base.exception.CustomException;
import com.learn.utils.result.ResultCodeEnum;
import com.learn.vod.service.VodService;
import com.learn.vod.util.AliyunVodSDK;
import com.learn.vod.util.VodProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.InputStream;
import java.util.List;

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
        //request.setTemplateGroupId(vodProperties.getTemplateGroupId());
        /* 工作流ID(可选) */
        //request.setWorkflowId(vodProperties.getWorkflowId());

        UploadVideoImpl uploader = new UploadVideoImpl();
        UploadStreamResponse response = uploader.uploadStream(request);

        String videoId = response.getVideoId();
        if (StringUtils.isEmpty(videoId)) {
            log.error("阿里云上传失败：" + response.getCode() + "-" + response.getMessage());
            throw new CustomException(ResultCodeEnum.VIDEO_UPLOAD_ALIYUN_ERROR);
        }
        return videoId;
    }

    /**
     * 删除视频
     *
     * @return DeleteVideoResponse 删除视频响应数据
     * @throws Exception
     */
    @Override
    public void removeVideo(String videoId) throws ClientException {
        DefaultAcsClient client = AliyunVodSDK.initVodClient(vodProperties.getKeyid(), vodProperties.getKeysecret());
        DeleteVideoRequest request = new DeleteVideoRequest();
        request.setVideoIds(videoId);
        client.getAcsResponse(request);
    }

    //批量删除视频
    @Override
    public void removeVideoByIdList(List<String> videoIdList) throws ClientException {
        DefaultAcsClient client = AliyunVodSDK.initVodClient(vodProperties.getKeyid(), vodProperties.getKeysecret());
        DeleteVideoRequest request = new DeleteVideoRequest();

        //id列表的长度
        int size = videoIdList.size();
        //组装好的字符串
        StringBuffer idListStr = new StringBuffer();
        for (int i = 0; i < size; i++) {
            idListStr.append(videoIdList.get(i));
            //假设 id 列表小于20   size<=20
            if (i == size - 1 || i % 20 == 19) {
                //删除
                //支持传入多个视频ID，多个用逗号分隔.id不能超过20
                log.info("idListStr = " + idListStr.toString());
                request.setVideoIds(idListStr.toString());
                client.getAcsResponse(request);
                //重置 idListStr
                idListStr = new StringBuffer();
            } else if (i % 20 < 19) {
                idListStr.append(",");
            }

        }

    }

    @Override
    public String getPlayAuth(String videoSourceId) throws ClientException {
        //初始化client对象
        DefaultAcsClient client = AliyunVodSDK.initVodClient(vodProperties.getKeyid(), vodProperties.getKeysecret());
        //创建请求对象
        GetVideoPlayAuthRequest request = new GetVideoPlayAuthRequest();
        //设置请求参数
        request.setVideoId(videoSourceId);
        //发送请求得到响应
        GetVideoPlayAuthResponse response =  client.getAcsResponse(request);
        return response.getPlayAuth();
    }

}
