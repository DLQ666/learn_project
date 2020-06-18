package com.learn.eduservice.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.learn.eduservice.entity.Subject;
import com.learn.eduservice.entity.excel.SubjectData;
import com.learn.eduservice.service.SubjectService;
import com.learn.service.base.exceptionhandler.CustomException;

/**
 * @program: learn_parent
 * @description: Excel监听器类
 * @author: Hasee
 * @create: 2020-06-17 15:58
 *
 * 因为SubjectExcelListener不能交给spring进行管理，需要自己new，不能注入其它对象
 * 因而不能实现数据库操作
 * 想要解决此问题 在service层new的时候手动注入进来
 */
public class SubjectExcelListener extends AnalysisEventListener<SubjectData> {

    public SubjectService eduSubjectService;

    public SubjectExcelListener() {
    }

    public SubjectExcelListener(SubjectService eduSubjectService) {
        this.eduSubjectService = eduSubjectService;
    }

    /**
    * @Description:  一行一行读取Excel内容
    * @Param: [subjectData, analysisContext] 
    * @return: void 
    * @Author: 
    * @Date: 2020/6/17 
    */
    @Override
    public void invoke(SubjectData subjectData, AnalysisContext analysisContext) {
        if (subjectData==null){
            throw new CustomException(20001,"文件数据为空");
        }
        //一行一行读取，每次读取有两个值，第一个值一级分类，第二个值二级分类
        //添加一级分类
        //判断一级分类是否重复
        Subject existOneSubject = this.existOneSubject(eduSubjectService, subjectData.getOneSubjectName());
        //判断如果为空则没有相同的一级分类，进行添加
        if (existOneSubject==null){
            existOneSubject=new Subject();
            existOneSubject.setParentId("0");
            //设置一级分类名称
            existOneSubject.setTitle(subjectData.getOneSubjectName());
            eduSubjectService.save(existOneSubject);
        }
        //获取一级分类id值
        String pid=existOneSubject.getId();
        //添加二级分类
        //判断二级分类是否重复
        Subject existTwoSubject = this.existTwoSubject(eduSubjectService, subjectData.getTwoSubjectName(), pid);
        if(existTwoSubject==null){
            existTwoSubject=new Subject();
            existTwoSubject.setParentId(pid);
            //设置二级分类名称
            existTwoSubject.setTitle(subjectData.getTwoSubjectName());
            eduSubjectService.save(existTwoSubject);
        }
    }

    /** 
    * @Description:  判断一级分类不能重复添加
    * @Param: [eduSubjectService, name] 
    * @return: com.learn.eduservice.entity.EduSubject 
    * @Author: 
    * @Date: 2020/6/17 
    */
    private Subject existOneSubject(SubjectService eduSubjectService,String name){
        QueryWrapper<Subject> wrapper=new QueryWrapper<>();
        wrapper.eq("title",name);
        wrapper.eq("parent_id","0");
        Subject oneSubject = eduSubjectService.getOne(wrapper);
        return oneSubject;
    }

    /**
    * @Description:  判断二级分类不能重复添加
    * @Param: [eduSubjectService, name]
    * @return: com.learn.eduservice.entity.EduSubject
    * @Author:
    * @Date: 2020/6/17
    */
    private Subject existTwoSubject(SubjectService eduSubjectService,String name,String pid){
        QueryWrapper<Subject> wrapper=new QueryWrapper<>();
        wrapper.eq("title",name);
        wrapper.eq("parent_id",pid);
        Subject twoSubject = eduSubjectService.getOne(wrapper);
        return twoSubject;
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }
}
