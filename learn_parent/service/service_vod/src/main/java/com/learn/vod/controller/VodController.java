package com.learn.vod.controller;

import com.learn.service.base.exception.CustomException;
import com.learn.utils.result.ResponseResult;
import com.learn.utils.result.ResultCodeEnum;
import com.learn.utils.utils.ExceptionUtil;
import com.learn.vod.service.VodService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * @program: learn_parent
 * @description: Vod前端控制器类
 * @author: Hasee
 * @create: 2020-06-30 20:46
 * @CrossOrigin: //跨域
 */
@Api(description="阿里云视频点播")
@Slf4j
@RestController
@RequestMapping("/eduVod/video")
public class VodController {

    @Autowired
    private VodService vodService;

    @PostMapping("/upload")
    public ResponseResult uploadVideo(@RequestParam("file") MultipartFile file) {
        try {
            InputStream inputStream = file.getInputStream();
            String originalFilename = file.getOriginalFilename();
            String videoId = vodService.uploadVideo(inputStream, originalFilename);
            return ResponseResult.ok().message("视频上传成功").data("videoId",videoId);
        } catch (IOException e) {
            log.error(ExceptionUtil.getMessage(e));
            throw new CustomException(ResultCodeEnum.VIDEO_UPLOAD_TOMCAT_ERROR);
        }
    }

    @DeleteMapping("/remove/{vodId}")
    public ResponseResult removeVideo(@PathVariable("vodId") String vodId){
        try {
            vodService.removeVideo(vodId);
            return ResponseResult.ok().message("视频删除成功");
        } catch (Exception e) {
            log.error(ExceptionUtil.getMessage(e));
            throw new CustomException(ResultCodeEnum.VIDEO_DELETE_ALIYUN_ERROR);
        }
    }

    @DeleteMapping("/remove")
    public ResponseResult removeVideoByIdList(@RequestBody List<String> videoIdList){

        try {
            vodService.removeVideoByIdList(videoIdList);
            return  ResponseResult.ok().message("视频删除成功");
        } catch (Exception e) {
            log.error(ExceptionUtil.getMessage(e));
            throw new CustomException(ResultCodeEnum.VIDEO_DELETE_ALIYUN_ERROR);
        }
    }
}
