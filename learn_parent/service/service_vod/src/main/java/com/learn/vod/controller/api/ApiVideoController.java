package com.learn.vod.controller.api;

import com.baomidou.mybatisplus.extension.api.R;
import com.learn.service.base.exception.CustomException;
import com.learn.utils.result.ResponseResult;
import com.learn.utils.result.ResultCodeEnum;
import com.learn.utils.utils.ExceptionUtil;
import com.learn.vod.service.VodService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @program: learn_parent
 * @description: 阿里云点播前端控制器
 * @author: Hasee
 * @create: 2020-07-03 14:39
 */
@Api(description="阿里云视频点播")
@Slf4j
@CrossOrigin
@RestController
@RequestMapping("/api/vod/media")
public class ApiVideoController {

    @Autowired
    private VodService vodService;

    @GetMapping("get-play-auth/{videoSourceId}")
    public ResponseResult getPlayAuth(@PathVariable String videoSourceId){

        try{
            String playAuth = vodService.getPlayAuth(videoSourceId);
            return  ResponseResult.ok().message("获取播放凭证成功").data("playAuth", playAuth);
        } catch (Exception e) {
            log.error(ExceptionUtil.getMessage(e));
            throw new CustomException(ResultCodeEnum.FETCH_PLAYAUTH_ERROR);
        }
    }
}
