package com.learn.eduservice.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.learn.eduservice.entity.Course;
import com.baomidou.mybatisplus.extension.service.IService;
import com.learn.eduservice.entity.form.CourseInfoForm;
import com.learn.eduservice.entity.query.CourseQuery;
import com.learn.eduservice.entity.vo.CoursePublishVo;
import com.learn.eduservice.entity.vo.CourseVo;
import com.learn.eduservice.entity.vo.WebCourseQueryVo;
import com.learn.eduservice.entity.vo.WebCourseVo;
import com.learn.service.base.dto.CourseDto;

import java.util.List;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author dlq
 * @since 2020-06-18
 */
public interface CourseService extends IService<Course> {

    /**
     * 保存课程和课程详情信息
     * @param courseInfoForm 接收前端页面传过来的表单数据
     * @return 新生成的课程id
     */
    String saveCourseInfo(CourseInfoForm courseInfoForm);

    /**
     * 根据课程Id查询课程信息
     * @param id 课程ID
     * @return 课程信息
     */
    CourseInfoForm getCourseInfoById(String id);

    /**
     * 更新课程信息
     * @param courseInfoForm 课程信息
     */
    void updateCourseInfoById(CourseInfoForm courseInfoForm);

    /**
     * 课程分页查询
     * @param page 页
     * @param size 每页记录数
     * @param courseQuery 查询对象
     * @return
     */
    Page<CourseVo> selectPage(long page, long size, CourseQuery courseQuery);

    /**
     * 删除课程封面
     * @param id 课程id
     * @return 成功失败
     */
    boolean removeCoverById(String id);

    /**
     * 删除课程
     * @param id 课程id
     * @return 成功失败
     */
    boolean removeCourseById(String id);

    /**
     * 查询课程发布页面信息
     * @param id 课程id
     * @return CoursePublishVo 发布页面信息实体类对象
     */
    CoursePublishVo getCoursePublishVoById(String id);

    /**
     * 发布课程
     * @param id 课程id
     * @return 成功失败
     */
    boolean publishCourseById(String id);

    /**
     * 门户课程列表查询
     * @param webCourseQueryVo 查询条件对象
     * @return 查询的课程列表
     */
    List<Course> webSelectList(WebCourseQueryVo webCourseQueryVo);

    /**
     * 获取课程信息并更新浏览量
     * @param id 课程id
     * @return
     */
    WebCourseVo selectWebCourseVoById(String id);

    /**
     * 查询热门课程
     * @return 热门课程列表
     */
    List<Course> selectHotCourse();

    /**
     * 根据课程id查询课程信息->内部远程调用接口
     * @param courseId
     * @return
     */
    CourseDto getCourseDtoById(String courseId);
}
