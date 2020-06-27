package com.learn.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.learn.eduservice.entity.Teacher;
import com.learn.eduservice.entity.query.TeacherQuery;
import com.learn.eduservice.feign.OssService;
import com.learn.eduservice.mapper.TeacherMapper;
import com.learn.eduservice.service.TeacherService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.learn.utils.result.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 讲师 服务实现类
 * </p>
 *
 * @author dlq
 * @since 2020-06-18
 */
@Service
public class TeacherServiceImpl extends ServiceImpl<TeacherMapper, Teacher> implements TeacherService {

    @Autowired
    private OssService ossService;

    /**
     * @param teacherPage 把分页所有数据封装到teacherPage对象里面 然后传进来
     * @param teacherQuery 查询条件
     * @return 查询列表
     */
    @Override
    public Page<Teacher> selectPage(Page<Teacher> teacherPage, TeacherQuery teacherQuery) {
        //显示分页查询列表
        //构建条件
        QueryWrapper<Teacher> wrapper =new QueryWrapper<>();
//      2、分页查询
        if (teacherQuery == null){
            return baseMapper.selectPage(teacherPage,wrapper);
        }
//      3、条件查询
        //多条件组合查询
        String name = teacherQuery.getName();
        Integer level = teacherQuery.getLevel();
        String joinDateBegin = teacherQuery.getJoinDateBegin();
        String joinDateEnd = teacherQuery.getJoinDateEnd();
        //判断条件值是否为空,如果不为空拼接条件
        if (!StringUtils.isEmpty(name)){
            //构建条件
            wrapper.likeRight("name",name);
        }
        if (level != null){
            wrapper.eq("level",level);
        }
        if (!StringUtils.isEmpty(joinDateBegin)){
            wrapper.ge("join_date",joinDateBegin);
        }
        if (!StringUtils.isEmpty(joinDateEnd)){
            wrapper.le("join_date",joinDateEnd);
        }
        //排序
        wrapper.orderByAsc("sort");
        return baseMapper.selectPage(teacherPage,wrapper);
    }

    /**
     * 根据关键字查询讲师名列表
     * @param key 关键字
     * @return 查询的列表
     */
    @Override
    public List<Map<String, Object>> selectNameList(String key) {
        QueryWrapper<Teacher> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("name");
        queryWrapper.likeRight("name",key);
        List<Map<String, Object>> list = baseMapper.selectMaps(queryWrapper);
        return list;
    }

    /**
     * 根据讲师id删除讲师头像
     * @param id 讲师id
     * @return 布尔值 true or false
     */
    @Override
    public boolean removeAvatarById(String id) {
        //根据id获取讲师avatar的url
        Teacher teacher = baseMapper.selectById(id);
        if (teacher !=null){
            String avatar = teacher.getAvatar();
            if (!StringUtils.isEmpty(avatar)){
                ResponseResult result = ossService.removeFile(avatar);
                return result.getSuccess();
            }
        }
        return false;
    }
}
