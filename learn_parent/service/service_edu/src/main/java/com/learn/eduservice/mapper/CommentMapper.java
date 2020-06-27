package com.learn.eduservice.mapper;

import com.learn.eduservice.entity.Comment;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 评论 Mapper 接口
 * </p>
 *
 * @author dlq
 * @since 2020-06-18
 */
@Mapper
@Component
public interface CommentMapper extends BaseMapper<Comment> {

}
