package com.learn.eduservice.service.impl;

import com.learn.eduservice.entity.Video;
import com.learn.eduservice.mapper.VideoMapper;
import com.learn.eduservice.service.VideoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 课程视频 服务实现类
 * </p>
 *
 * @author dlq
 * @since 2020-06-18
 */
@Service
public class VideoServiceImpl extends ServiceImpl<VideoMapper, Video> implements VideoService {

}
