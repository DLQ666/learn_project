package com.learn.eduservice.service.impl;

import com.learn.eduservice.entity.Comment;
import com.learn.eduservice.mapper.CommentMapper;
import com.learn.eduservice.service.CommentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 评论 服务实现类
 * </p>
 *
 * @author dlq
 * @since 2020-06-18
 */
@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {

}
