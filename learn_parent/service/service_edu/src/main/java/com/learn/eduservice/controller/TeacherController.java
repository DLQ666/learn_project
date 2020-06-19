package com.learn.eduservice.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.learn.eduservice.entity.Teacher;
import com.learn.eduservice.entity.query.TeacherQuery;
import com.learn.eduservice.service.TeacherService;
import com.learn.utils.result.ResponseResult;
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
 * 讲师 前端控制器
 * </p>
 *
 * @author dlq
 * @since 2020-06-18\
 * @CrossOrigin: 允许跨域
 */
@CrossOrigin
@Api(description = "讲师管理")
@RestController
@RequestMapping("/eduservice/teacher")
public class TeacherController {

    @Autowired
    private TeacherService teacherService;

    @ApiOperation(value = "所有讲师列表")
    @GetMapping("/findAll")
    public ResponseResult findAll() {
        List<Teacher> list = teacherService.list(null);
        return ResponseResult.ok().data("items", list);
    }

    @ApiOperation(value = "根据ID删除讲师")
    @DeleteMapping("/{id}")
    public ResponseResult delTeacher(@ApiParam(name = "id", value = "讲师ID", required = true) @PathVariable("id") String id) {
        Teacher findById = teacherService.getById(id);
        if (findById != null) {
            boolean flag = teacherService.removeById(id);
            if (flag) {
                return ResponseResult.ok().message("删除成功");
            } else {
                return ResponseResult.error().message("数据不存在");
            }
        }
        return ResponseResult.error().message("数据不存在");
    }

    @ApiOperation(value = "分页查询讲师列表")
    @GetMapping("/pageTeacherList/{page}/{size}")
    public ResponseResult pageList(@PathVariable("page") long page, @PathVariable("size") long size) {
        //创建page对象
        Page<Teacher> teacherPage = new Page<>(page, size);
        /*try {
            int i=10/0;
        } catch (Exception e) {
            throw new CustomException(20001,"执行了自定义异常处理");
        }*/
        //调用方法实现分页
        //调用方法的时候，底层封装。把分页所有数据封装到teacherPage对象里面
        teacherService.page(teacherPage);
        //总记录数
        long total = teacherPage.getTotal();
        //数据list集合
        List<Teacher> rows = teacherPage.getRecords();
        Map map=new HashMap();
        map.put("total",total);
        map.put("rows",rows);
        return ResponseResult.ok().data(map);
        //第二种写法 直接写下边的  不用创建Map了
//       return ResponseResult.ok().data("total",total).data("rows",rows);
    }

    @ApiOperation(value = "分页条件查询讲师列表")
    @PostMapping("/queryPageTeacherList/{page}/{size}")
    public ResponseResult queryPageList(@PathVariable("page") long page, @PathVariable("size") long size,@RequestBody(required = false) TeacherQuery teacherQuery){
        //创建page对象
        Page<Teacher> teacherPage = new Page<>(page, size);
        //调用方法实现分页
        //调用方法的时候，底层封装。把分页所有数据封装到teacherPage对象里面
        Page<Teacher> pageModel = teacherService.selectPage(teacherPage, teacherQuery);
        //总记录数
        long total = pageModel.getTotal();
        //数据list集合
        List<Teacher> rows = pageModel.getRecords();
        Map map=new HashMap();
        map.put("total",total);
        map.put("rows",rows);
        return ResponseResult.ok().data(map);
    }

    @ApiOperation(value = "添加讲师")
    @PostMapping("/addTeacher")
    public ResponseResult addTeacher(@RequestBody Teacher teacher){
        boolean save = teacherService.save(teacher);
        if (save){
            return ResponseResult.ok().message("添加成功");
        }else {
            return ResponseResult.error().message("添加失败");
        }
    }

    @ApiOperation(value = "根据讲师id查询信息")
    @GetMapping("/getTeacher/{id}")
    public ResponseResult getTeacher(@PathVariable("id") String id){
        Teacher teacher = teacherService.getById(id);
        if (teacher !=null){
            return ResponseResult.ok().data("teacher",teacher);
        }else {
            return ResponseResult.error().message("数据不存在");
        }
    }

    @ApiOperation(value = "根据讲师id进行修改")
    @PutMapping("/updateTeacher")
    public ResponseResult updateTeacher(@RequestBody Teacher teacher){
        boolean flag = teacherService.updateById(teacher);
        if (flag){
            return ResponseResult.ok().message("修改成功");
        }else {
            return ResponseResult.error().message("数据不存在");
        }
    }
}

