package com.learn.eduservice.service;

import com.learn.eduservice.entity.Chapter;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author dlq
 * @since 2020-06-18
 */
public interface ChapterService extends IService<Chapter> {

    /**
     * 根据章节id删除章节
     * @param id 章节ID
     * @return 成功/失败
     */
    boolean removeChapterById(String id);
}
