package com.learn.eduservice.entity.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

/**
 * @program: learn_parent
 * @description: Excel表实体类
 * @author: Hasee
 * @create: 2020-06-17 15:53
 */
@Data
public class ExcelSubjectData {

    @ExcelProperty(value = "一级分类",index = 0)
    private String levelOneTitle;

    @ExcelProperty(value = "二级分类",index = 1)
    private String levelTwoTitle;
}
