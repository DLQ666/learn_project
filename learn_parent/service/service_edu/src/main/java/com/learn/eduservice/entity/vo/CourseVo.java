package com.learn.eduservice.entity.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @program: learn_parent
 * @description: 课程查询结果实体类
 * @author: Hasee
 * @create: 2020-06-26 19:17
 */
@Data
public class CourseVo implements Serializable {

    private static final long serialVersionUID = 1L;
    private String id;
    private String title;
    private String subjectParentTitle;
    private String subjectTitle;
    private String teacherName;
    private Integer lessonNum;
    private String price;
    private String cover;
    private Long buyCount;
    private Long viewCount;
    private String status;
    private String gmtCreate;
}
