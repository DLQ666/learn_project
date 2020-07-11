package com.learn.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.learn.eduservice.entity.Video;
import com.learn.eduservice.feign.VodService;
import com.learn.eduservice.mapper.VideoMapper;
import com.learn.eduservice.service.VideoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程视频 服务实现类
 * </p>
 *
 * @author dlq
 * @since 2020-06-18
 */
@Service
@Slf4j
public class VideoServiceImpl extends ServiceImpl<VideoMapper, Video> implements VideoService {

    /**
     * 注入远程调用Vod接口
     */
    @Autowired
    private VodService vodService;

    @Override
    public void removeVideoById(String id) {
        //根据videoid(传进来的id) 找到视频id
        Video video = baseMapper.selectById(id);
        String videoSourceId = video.getVideoSourceId();
        //在此处调用vod中的删除视频文件的接口
        vodService.removeVideo(videoSourceId);
    }

    /**
     * 在video表中根据 章节id(chapter_id)  查询课时id(video_source_id) 列表
     * @param chapterId 章节id
     */
    @Override
    public void removeVideoByChapterId(String chapterId) {
        QueryWrapper<Video> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("video_source_id");
        queryWrapper.eq("chapter_id",chapterId);

        List<Map<String, Object>> maps = baseMapper.selectMaps(queryWrapper);
        List<String> videoSourceIdList = this.getVideoSourceIdList(maps);
        vodService.removeVideoByIdList(videoSourceIdList);
    }

    /**
     * 在video表中根据 课程id(course_id)  查询课时id(video_source_id) 列表
     * @param courseId 课程id
     */
    @Override
    public void removeVideoByCourseId(String courseId) {
        QueryWrapper<Video> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("video_source_id");
        queryWrapper.eq("course_id",courseId);

        List<Map<String, Object>> maps = baseMapper.selectMaps(queryWrapper);
        List<String> videoSourceIdList = this.getVideoSourceIdList(maps);
        vodService.removeVideoByIdList(videoSourceIdList);
    }

    /**
     * 组装视频id列表
     * @param maps 视频id的maps集合
     * @return  视频id列表
     */
    private List<String> getVideoSourceIdList(List<Map<String ,Object>> maps){
        ArrayList<String> videoSourceIdList = new ArrayList<>();
        for (Map<String, Object> map : maps) {
            if (map == null){
                return videoSourceIdList;
            }
            String videoSourceId = (String) map.get("video_source_id");
            videoSourceIdList.add(videoSourceId);
        }
        return videoSourceIdList;
    }
}
