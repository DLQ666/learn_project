package com.learn.eduservice.mapper;

import com.learn.eduservice.entity.Teacher;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

/**
 * <p>
 * 讲师 Mapper 接口
 * </p>
 *
 * @author dlq
 * @since 2020-06-18
 */
@Mapper
@Component
public interface TeacherMapper extends BaseMapper<Teacher> {

}
