package com.learn.service.base.dto;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @program: learn_parent
 * @description: 公共数据传输实体类 -> (课程信息dto实体类)
 * @author: Hasee
 * @create: 2020-07-06 13:01
 */
@Data
public class CourseDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;//课程ID
    private String title;//课程标题
    private BigDecimal price;//课程销售价格，设置为0则可免费观看
    private String cover;//课程封面图片路径
    private String teacherName;//课程讲师
}
