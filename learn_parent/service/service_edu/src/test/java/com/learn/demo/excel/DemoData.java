package com.learn.demo.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

/**
 * @program: learn_parent
 * @description: 测试Excel实体类
 * @author: Hasee
 * @create: 2020-06-17 15:20
 */
@Data
public class DemoData {

    //设置Excel表头名称
    @ExcelProperty(value = "学生编号",index = 0)
    private Integer sno;

    @ExcelProperty(value = "学生姓名",index = 1)
    private String sname;

}
