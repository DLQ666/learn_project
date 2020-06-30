package com.learn.eduservice.entity.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @program: learn_parent
 * @description: 章节  课时视频列表 实体类
 * @author: Hasee
 * @create: 2020-06-27 21:44
 */
@Data
public class VideoVo implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;
    private String title;
    private Boolean free;
    private Integer sort;

    private String videoSourceId;
}
