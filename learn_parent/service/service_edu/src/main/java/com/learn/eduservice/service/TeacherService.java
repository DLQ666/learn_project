package com.learn.eduservice.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.learn.eduservice.entity.Teacher;
import com.baomidou.mybatisplus.extension.service.IService;
import com.learn.eduservice.entity.query.TeacherQuery;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 讲师 服务类
 * </p>
 *
 * @author dlq
 * @since 2020-06-18
 */
public interface TeacherService extends IService<Teacher> {

    /**
     * 分页条件查询讲师列表
     * @param teacherPage 把分页所有数据封装到teacherPage对象里面 然后传进来
     * @param teacherQuery 查询条件
     * @return 根据条件以及页码 查询列表
     */
    Page<Teacher> selectPage(Page<Teacher> teacherPage, TeacherQuery teacherQuery);

    /**
     * 根据关键字查询讲师名列表
     * @param key 关键字
     * @return 根据关键字查询的列表
     */
    List<Map<String, Object>> selectNameList(String key);

    /**
     * 根据id删除讲师头像
     * @param id 讲师id
     * @return 布尔值
     */
    boolean removeAvatarById(String id);

    /**
     * 根据讲师id 获取讲师详情页信息 包括讲师一些基本信息 和 该讲师所讲课程
     * @param id 讲师id
     * @return map集合类型的讲师详情页信息
     */
    Map<String,Object> selectTeacherInfoById(String id);

    /**
     * 查询推荐讲师
     * @return 推荐讲师列表
     */
    List<Teacher> selectHotTeacher();
}
