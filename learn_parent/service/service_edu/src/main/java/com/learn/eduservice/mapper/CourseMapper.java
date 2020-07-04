package com.learn.eduservice.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.learn.eduservice.entity.Course;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.learn.eduservice.entity.vo.CoursePublishVo;
import com.learn.eduservice.entity.vo.CourseVo;
import com.learn.eduservice.entity.vo.WebCourseVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * <p>
 * 课程 Mapper 接口
 * </p>
 *
 * @author dlq
 * @since 2020-06-18
 */
@Mapper
@Component
public interface CourseMapper extends BaseMapper<Course> {

    List<CourseVo> selectPageByCourseQuery(Page<CourseVo> pageParam,@Param(Constants.WRAPPER) QueryWrapper<CourseVo> queryWrapper);

    CoursePublishVo selectCoursePublishVoById(String id);

    WebCourseVo selectWebCourseVoById(String courseId);
}
