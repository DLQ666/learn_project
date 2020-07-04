package com.learn.eduservice.controller.api;

import com.baomidou.mybatisplus.extension.api.R;
import com.learn.eduservice.entity.Teacher;
import com.learn.eduservice.service.TeacherService;
import com.learn.utils.result.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @program: learn_parent
 * @description: 前台门户讲师列表前端控制器类
 * @author: Hasee
 * @create: 2020-07-01 19:55
 */
@CrossOrigin
@Api(description="讲师")
@RestController
@RequestMapping("/api/edu/teacher")
public class ApiTeacherController {

    @Autowired
    private TeacherService teacherService;

    @ApiOperation(value="所有讲师列表")
    @GetMapping("/list")
    @Cacheable(value = "index",key = "'list'")
    public ResponseResult listAll(){
        List<Teacher> list = teacherService.list(null);
        return ResponseResult.ok().data("items", list).message("获取讲师列表成功");
    }

    @ApiOperation(value = "获取讲师")
    @GetMapping("/get/{id}")
    public ResponseResult get(@PathVariable("id") String id) {
        Map<String, Object> map = teacherService.selectTeacherInfoById(id);
        return ResponseResult.ok().data(map);
    }
}
