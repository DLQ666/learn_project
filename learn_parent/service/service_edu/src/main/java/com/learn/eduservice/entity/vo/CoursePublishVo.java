package com.learn.eduservice.entity.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @program: learn_parent
 * @description: 课程发布展示页面数据实体类
 * @author: Hasee
 * @create: 2020-06-27 17:02
 */
@Data
public class CoursePublishVo implements Serializable {

    private static final long serialVersionUID = 1L;
    private String id;
    private String title;
    private String cover;
    private Integer lessonNum;
    private String subjectParentTitle;
    private String subjectTitle;
    private String teacherName;
    private String price;//只用于显示
}
