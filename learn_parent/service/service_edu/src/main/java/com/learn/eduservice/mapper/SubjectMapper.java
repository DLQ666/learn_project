package com.learn.eduservice.mapper;

import com.learn.eduservice.entity.Subject;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.learn.eduservice.entity.query.SubjectQuery;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * <p>
 * 课程科目 Mapper 接口
 * </p>
 *
 * @author dlq
 * @since 2020-06-18
 */
@Mapper
@Component
public interface SubjectMapper extends BaseMapper<Subject> {

    List<SubjectQuery> selectNestedListByParentId(String parentId);
}
