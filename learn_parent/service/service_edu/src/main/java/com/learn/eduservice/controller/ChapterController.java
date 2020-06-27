package com.learn.eduservice.controller;


import com.baomidou.mybatisplus.extension.api.R;
import com.learn.eduservice.entity.Chapter;
import com.learn.eduservice.service.ChapterService;
import com.learn.utils.result.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author dlq
 * @since 2020-06-18
 */
@Slf4j
@CrossOrigin
@Api(description = "章节管理")
@RestController
@RequestMapping("/eduservice/chapter")
public class ChapterController {

    @Autowired
    private ChapterService chapterService;

    @ApiOperation("新增章节")
    @PostMapping("/save")
    public ResponseResult save(@RequestBody Chapter chapter){
        boolean result = chapterService.save(chapter);
        if (result) {
            return ResponseResult.ok().message("保存成功");
        } else {
            return ResponseResult.error().message("保存失败");
        }
    }

    @ApiOperation("根据id查询章节")
    @GetMapping("/getChapterById/{id}")
    public ResponseResult getChapterById(@PathVariable String id){
        Chapter chapter = chapterService.getById(id);
        if (chapter != null) {
            return ResponseResult.ok().data("item", chapter);
        } else {
            return ResponseResult.error().message("数据不存在");
        }
    }

    @ApiOperation("根据id修改章节")
    @PutMapping("/update")
    public ResponseResult updateById(@RequestBody Chapter chapter){
        boolean result = chapterService.updateById(chapter);
        if (result) {
            return ResponseResult.ok().message("修改成功");
        } else {
            return ResponseResult.error().message("章节数据不存在");
        }
    }

    @ApiOperation("根据ID删除章节")
    @DeleteMapping("remove/{id}")
    public ResponseResult removeById(
            @ApiParam(value = "章节ID", required = true)
            @PathVariable String id){

        //TODO：删除课程视频
        //此处调用vod中删除视频文件的接口

        //删除章节
        boolean flag = chapterService.removeChapterById(id);
        if (flag) {
            return ResponseResult.ok().message("删除成功");
        } else {
            return ResponseResult.error().message("数据不存在");
        }
    }
}

