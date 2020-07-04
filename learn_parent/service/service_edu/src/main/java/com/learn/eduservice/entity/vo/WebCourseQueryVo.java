package com.learn.eduservice.entity.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @program: learn_parent
 * @description: 前台课程分类排序查询实体类
 * @author: Hasee
 * @create: 2020-07-02 16:50
 */
@Data
public class WebCourseQueryVo implements Serializable {

    private static final long serialVersionUID = 1L;
    private String subjectParentId;
    private String subjectId;
    private String buyCountSort;
    private String gmtCreateSort;
    private String priceSort;
    private Integer type;
}
