package com.learn.eduservice.entity.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @program: learn_parent
 * @description: 章节 章节列表实体类
 * @author: Hasee
 * @create: 2020-06-27 21:44
 */
@Data
public class ChapterVo implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;
    private String title;
    private Integer sort;
    private List<VideoVo> children = new ArrayList<>();
}
