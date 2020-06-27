package com.learn.eduservice.entity.query;

import lombok.Data;

import java.io.Serializable;

/**
 * @program: learn_parent
 * @description: 课程搜索条件实体类
 * @author: Hasee
 * @create: 2020-06-26 19:33
 */
@Data
public class CourseQuery implements Serializable {

    private static final long serialVersionUID = 1L;

    private String title;
    private String teacherId;
    private String subjectParentId;
    private String subjectId;
}
