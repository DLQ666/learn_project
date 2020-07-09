package com.learn.eduservice.controller;


import com.learn.eduservice.entity.query.SubjectQuery;
import com.learn.eduservice.service.SubjectService;
import com.learn.service.base.exception.CustomException;
import com.learn.utils.result.ResponseResult;
import com.learn.utils.result.ResultCodeEnum;
import com.learn.utils.utils.ExceptionUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.List;

/**
 * <p>
 * 课程科目 前端控制器
 * </p>
 *
 * @author dlq
 * @since 2020-06-18
 */
@Slf4j
@Api(description = "课程分类管理")
@RestController
@RequestMapping("/eduservice/subject")
public class SubjectController {

    @Autowired
    private SubjectService subjectService;

    @ApiOperation(value = "excel批量导入课程分类")
    @PostMapping("/addSubject")
    public ResponseResult batchImport(@RequestParam("file") MultipartFile file){
        try {
            InputStream inputStream = file.getInputStream();
            subjectService.batchImport(inputStream);
            return ResponseResult.ok().message("批量导入成功");
        } catch (Exception e) {
            log.error(ExceptionUtil.getMessage(e));
            throw new CustomException(ResultCodeEnum.EXCEL_DATA_IMPORT_ERROR);
        }
    }

    @ApiOperation(value = "课程分类列表")
    @GetMapping("/subjectList")
    public ResponseResult nestedList(){
        List<SubjectQuery> list = subjectService.subjectList();
        return ResponseResult.ok().data("items",list);
    }

}

