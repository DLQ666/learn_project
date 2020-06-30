package com.learn.eduservice.controller;


import com.baomidou.mybatisplus.extension.api.R;
import com.learn.eduservice.entity.Video;
import com.learn.eduservice.service.VideoService;
import com.learn.utils.result.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 课程视频 前端控制器
 * </p>
 *
 * @author dlq
 * @since 2020-06-18
 */
@Slf4j
@CrossOrigin
@Api(description = "课时管理")
@RestController
@RequestMapping("/eduservice/video")
public class VideoController {

    @Autowired
    private VideoService videoService;

    @ApiOperation("新增课时")
    @PostMapping("/save")
    public ResponseResult save(@RequestBody Video video) {
        boolean result = videoService.save(video);
        if (result) {
            return ResponseResult.ok().message("保存成功");
        } else {
            return ResponseResult.error().message("保存失败");
        }
    }

    @ApiOperation("根据id查询课时")
    @GetMapping("/get/{id}")
    public ResponseResult getById(@PathVariable String id) {
        Video video = videoService.getById(id);
        if (video != null) {
            return ResponseResult.ok().data("item", video);
        } else {
            return ResponseResult.error().message("数据不存在");
        }
    }

    @ApiOperation("根据id修改课时")
    @PutMapping("/update")
    public ResponseResult updateById(@RequestBody Video video) {
        boolean result = videoService.updateById(video);
        if (result) {
            return ResponseResult.ok().message("修改成功");
        } else {
            return ResponseResult.error().message("数据不存在");
        }
    }

    @ApiOperation("根据ID删除课时")
    @DeleteMapping("/remove/{id}")
    public ResponseResult removeById(@PathVariable String id) {

        //TODO 删除视频：VOD
        //在此处调用vod中的删除视频文件的接口

        boolean result = videoService.removeById(id);
        if (result) {
            return ResponseResult.ok().message("删除成功");
        } else {
            return ResponseResult.error().message("数据不存在");
        }
    }
}

