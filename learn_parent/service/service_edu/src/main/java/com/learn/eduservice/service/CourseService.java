package com.learn.eduservice.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.learn.eduservice.entity.Course;
import com.baomidou.mybatisplus.extension.service.IService;
import com.learn.eduservice.entity.form.CourseInfoForm;
import com.learn.eduservice.entity.query.CourseQuery;
import com.learn.eduservice.entity.vo.CoursePublishVo;
import com.learn.eduservice.entity.vo.CourseVo;

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

    void updateCourseInfoById(CourseInfoForm courseInfoForm);

    Page<CourseVo> selectPage(long page, long size, CourseQuery courseQuery);

    boolean removeCoverById(String id);

    boolean removeCourseById(String id);

    CoursePublishVo getCoursePublishVoById(String id);

    boolean publishCourseById(String id);
}
