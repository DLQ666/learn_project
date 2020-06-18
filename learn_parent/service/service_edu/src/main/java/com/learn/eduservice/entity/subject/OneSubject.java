package com.learn.eduservice.entity.subject;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @program: learn_parent
 * @description: 一级分类实体类
 * @author: Hasee
 * @create: 2020-06-17 17:46
 */
@Data
public class OneSubject {

    private String id;
    private String title;

    /**
     * 一个一级分类有多个二级分类
     */
    private List<TwoSubject> children=new ArrayList<>();
}
