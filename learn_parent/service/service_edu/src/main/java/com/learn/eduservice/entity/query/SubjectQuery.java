package com.learn.eduservice.entity.query;

import com.learn.eduservice.entity.Subject;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @program: learn_parent
 * @description: 课程树结构实体类
 * @author: Hasee
 * @create: 2020-06-25 12:11
 */
@Data
public class SubjectQuery implements Serializable {

    private static final long serialVersionUID = 1L;
    private String id;
    private String title;
    private Integer sort;

    private List<SubjectQuery> children = new ArrayList<>();
}
