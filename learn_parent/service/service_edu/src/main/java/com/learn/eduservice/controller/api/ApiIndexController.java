package com.learn.eduservice.controller.api;

import com.learn.eduservice.entity.Course;
import com.learn.eduservice.entity.Teacher;
import com.learn.eduservice.service.CourseService;
import com.learn.eduservice.service.TeacherService;
import com.learn.utils.result.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @program: learn_parent
 * @description: 门户首页获取课程以及讲师信息前端控制器类
 * @author: Hasee
 * @create: 2020-07-04 11:20
 */
@Api(description="首页")
@RestController
@RequestMapping("/api/edu/index")
public class ApiIndexController {

    @Autowired
    private CourseService courseService;

    @Autowired
    private TeacherService teacherService;

    @ApiOperation("课程和讲师的首页数据")
    @GetMapping()
    public ResponseResult index(){
        //查询热门课程
        List<Course> courseList = courseService.selectHotCourse();

        //查询推荐讲师
        List<Teacher> teacherList = teacherService.selectHotTeacher();

        return ResponseResult.ok().data("courseList",courseList).data("teacherList",teacherList);
    }
}
