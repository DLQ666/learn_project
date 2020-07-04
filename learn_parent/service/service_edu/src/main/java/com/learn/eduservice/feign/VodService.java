package com.learn.eduservice.feign;

import com.learn.eduservice.feign.fallback.OssServiceFallBack;
import com.learn.eduservice.feign.fallback.VodServiceFallBack;
import com.learn.utils.result.ResponseResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * @description: 阿里云Vod远程调用接口
 * @author: Hasee
 * @create: 2020-07-01 11:39
 */
@Component
@FeignClient(value = "service-vod",fallback = VodServiceFallBack.class)
public interface VodService {

    /**
     * 调用vod中的删除视频文件的接口
     * @param vodId 视频id
     * @return
     */
    @DeleteMapping("/eduVod/video/remove/{vodId}")
    public ResponseResult removeVideo(@PathVariable("vodId") String vodId);

    /**
     * 调用vod中的批量删除视频文件的接口
     * @param videoIdList 视频id列表
     * @return
     */
    @DeleteMapping("/eduVod/video/remove")
    public ResponseResult removeVideoByIdList(@RequestBody List<String> videoIdList);
}
