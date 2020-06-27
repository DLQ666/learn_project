package com.learn.eduservice.mapper;

import com.learn.eduservice.entity.CourseCollect;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 课程收藏 Mapper 接口
 * </p>
 *
 * @author dlq
 * @since 2020-06-18
 */
@Mapper
@Component
public interface CourseCollectMapper extends BaseMapper<CourseCollect> {

}
