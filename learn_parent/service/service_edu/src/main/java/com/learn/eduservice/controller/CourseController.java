package com.learn.eduservice.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.learn.eduservice.entity.form.CourseInfoForm;
import com.learn.eduservice.entity.query.CourseQuery;
import com.learn.eduservice.entity.vo.CoursePublishVo;
import com.learn.eduservice.entity.vo.CourseVo;
import com.learn.eduservice.service.CourseService;
import com.learn.service.base.exception.CustomException;
import com.learn.utils.result.ResponseResult;
import com.learn.utils.result.ResultCodeEnum;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author dlq
 * @CrossOrigin 跨域
 * @since 2020-06-18
 */
@Api(description = "课程管理")
@CrossOrigin
@RestController
@RequestMapping("/eduservice/course")
public class CourseController {

    @Autowired
    CourseService courseService;

    @ApiOperation("新增课程")
    @PostMapping("/saveCourseInfo")
    public ResponseResult saveCourseInfo(@RequestBody CourseInfoForm courseInfoForm) {
        if (courseInfoForm.getTitle() == null) {
            throw new CustomException(ResultCodeEnum.COURSE_INFO_FORM_IS_NULL);
        }
        String courseId = courseService.saveCourseInfo(courseInfoForm);
        return ResponseResult.ok().data("courseId", courseId).message("保存成功");
    }

    @ApiOperation("根据ID查询课程")
    @GetMapping("/getCourseInfoById/{id}")
    public ResponseResult getCourseById(@PathVariable("id") String id) {
        CourseInfoForm courseInfoForm = courseService.getCourseInfoById(id);
        if (courseInfoForm != null) {
            return ResponseResult.ok().data("item", courseInfoForm);
        } else {
            return ResponseResult.ok().message("数据不存在");
        }
    }

    @ApiOperation("更新课程")
    @PutMapping("/updateCourseInfoById")
    public ResponseResult updateCourseById(@RequestBody CourseInfoForm courseInfoForm) {
        courseService.updateCourseInfoById(courseInfoForm);
        return ResponseResult.ok().message("修改成功");
    }

    @ApiOperation(value = "分页条件查询课程列表")
    @GetMapping("/queryPageCourseList/{page}/{size}")
    public ResponseResult queryPageList(@PathVariable("page") long page, @PathVariable("size") long size, CourseQuery courseQuery) {
        //调用方法的时候，底层封装。把分页所有数据封装到teacherPage对象里面
        Page<CourseVo> pageModel = courseService.selectPage(page,size, courseQuery);
        //总记录数
        long total = pageModel.getTotal();
        //数据list集合h
        List<CourseVo> rows = pageModel.getRecords();
        Map map = new HashMap();
        map.put("total", total);
        map.put("rows", rows);
        return ResponseResult.ok().data(map);
    }

    @ApiOperation(value = "根据ID删除课程")
    @DeleteMapping("/{id}")
    public ResponseResult delCourse(@ApiParam(name = "id", value = "课程ID", required = true) @PathVariable("id") String id) {

        //TODO：删除课程视频
        //此处调用vod中删除视频文件的接口

        //删除课程封面
        courseService.removeCoverById(id);

        //删除课程
        boolean flag = courseService.removeCourseById(id);
        if (flag) {
            return ResponseResult.ok().message("删除成功");
        } else {
            return ResponseResult.error().message("数据不存在");
        }
    }

    @ApiOperation("根据ID获取课程发布信息")
    @GetMapping("/coursePublish/{id}")
    public ResponseResult getCoursePublishVoById(@PathVariable("id") String id){
        CoursePublishVo coursePublishVo = courseService.getCoursePublishVoById(id);
        if (coursePublishVo != null) {
            return ResponseResult.ok().data("item", coursePublishVo);
        } else {
            return ResponseResult.error().message("数据不存在");
        }
    }

    @ApiOperation("根据id发布课程")
    @PutMapping("/publishCourse/{id}")
    public ResponseResult publishCourseById(@PathVariable("id") String id){
        boolean result = courseService.publishCourseById(id);
        if (result) {
            return ResponseResult.ok().message("发布成功");
        } else {
            return ResponseResult.error().message("课程不存在");
        }
    }

}

