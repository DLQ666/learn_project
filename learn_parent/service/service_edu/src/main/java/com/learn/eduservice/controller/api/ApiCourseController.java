package com.learn.eduservice.controller.api;

import com.learn.eduservice.entity.Course;
import com.learn.eduservice.entity.vo.ChapterVo;
import com.learn.eduservice.entity.vo.WebCourseQueryVo;
import com.learn.eduservice.entity.vo.WebCourseVo;
import com.learn.eduservice.service.ChapterService;
import com.learn.eduservice.service.CourseService;
import com.learn.service.base.dto.CourseDto;
import com.learn.utils.result.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @program: learn_parent
 * @description: 前台门户课程列表查询前端控制器
 * @author: Hasee
 * @create: 2020-07-02 16:51
 */
@Api(description = "课程")
@Slf4j
@CrossOrigin
@RestController
@RequestMapping("/api/edu/course")
public class ApiCourseController {

    @Autowired
    private CourseService courseService;
    @Autowired
    private ChapterService chapterService;

    @ApiOperation("课程列表查询")
    @GetMapping("/list")
    public ResponseResult pageList(WebCourseQueryVo webCourseQueryVo) {
        List<Course> courseList = courseService.webSelectList(webCourseQueryVo);
        return ResponseResult.ok().data("courseList", courseList);
    }

    @ApiOperation("根据课程id查询课程信息")
    @GetMapping("/get/{courseId}")
    public ResponseResult getById(@PathVariable("courseId") String courseId) {
        //查询课程信息和讲师信息
        WebCourseVo webCourseVo = courseService.selectWebCourseVoById(courseId);
        //查询当前课程的嵌套章节和课时信息
        List<ChapterVo> chapterVoList = chapterService.nestedList(courseId);
        return ResponseResult.ok().data("course", webCourseVo).data("chapterVoList", chapterVoList);
    }

    @ApiOperation("根据课程id查询课程信息")
    @GetMapping("/inner/getCourseDto/{courseId}")
    public CourseDto getCourseDtoById(@PathVariable("courseId") String courseId) {
        CourseDto courseDto = courseService.getCourseDtoById(courseId);
        return courseDto;
    }
}
