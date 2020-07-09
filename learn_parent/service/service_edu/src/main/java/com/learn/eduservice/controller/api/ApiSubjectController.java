package com.learn.eduservice.controller.api;

import com.learn.eduservice.entity.query.SubjectQuery;
import com.learn.eduservice.service.SubjectService;
import com.learn.utils.result.ResponseResult;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @program: learn_parent
 * @description: 门户查询课程分类前端控制器类
 * @author: Hasee
 * @create: 2020-07-02 17:33
 */
@Api(description="课程分类")
@Slf4j
@RestController
@RequestMapping("/api/edu/subject")
public class ApiSubjectController {

    @Autowired
    private SubjectService subjectService;

    @GetMapping("/nestedList")
    public ResponseResult nestedList(){
        List<SubjectQuery> subjectQueryList = subjectService.subjectList();
        return ResponseResult.ok().data("items",subjectQueryList);
    }
}
