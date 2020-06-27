package com.learn.eduservice.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.learn.eduservice.entity.Subject;
import com.learn.eduservice.entity.excel.ExcelSubjectData;
import com.learn.eduservice.mapper.SubjectMapper;
import com.learn.eduservice.service.SubjectService;
import com.learn.service.base.exception.CustomException;
import com.learn.utils.result.ResultCodeEnum;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

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
@Slf4j
public class SubjectExcelListener extends AnalysisEventListener<ExcelSubjectData> {

    public SubjectMapper subjectMapper;

    public SubjectExcelListener() {
    }

    public SubjectExcelListener(SubjectMapper subjectMapper) {
        this.subjectMapper = subjectMapper;
    }

    /**
    * @Description:  一行一行读取Excel内容
    * @Param: [subjectData, analysisContext] 
    * @return: void 
    * @Author: 
    * @Date: 2020/6/17 
    */
    @Override
    public void invoke(ExcelSubjectData subjectData, AnalysisContext analysisContext) {
        if (subjectData == null) {
            throw new CustomException(ResultCodeEnum.EXCEL_DATA_ISNULL);
        }
        /*//一行一行读取，每次读取有两个值，第一个值一级分类，第二个值二级分类
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
        }*/

        log.info("解析到一条记录：{}", subjectData);
        //处理读取出来的数据
        //一级标题
        String levelOneTitle = subjectData.getLevelOneTitle();
        //二级标题
        String levelTwoTitle = subjectData.getLevelTwoTitle();
        log.info("levelOneTitle：{}", levelOneTitle);
        log.info("levelTwoTitle：{}", levelTwoTitle);

        //判断一级类别数据是否存在
        Subject oneSubject = this.existOneSubject(levelOneTitle);
        if (oneSubject == null){
            //组装一级类别
            oneSubject = new Subject();
            oneSubject.setParentId("0");
            oneSubject.setTitle(levelOneTitle);
            //存入数据库
            subjectMapper.insert(oneSubject);
        }
        //获取一级分类id值
        String pid = oneSubject.getId();
        //判断二级类别数据是否存在
        Subject twoSubject = this.existTwoSubject(levelTwoTitle, pid);
        if (twoSubject == null){
            twoSubject = new Subject();
            twoSubject.setTitle(levelTwoTitle);
            twoSubject.setParentId(pid);
            //存入数据库
            subjectMapper.insert(twoSubject);
        }
    }

    /** 
    * @Description:  判断一级分类不能重复添加
    * @Param: [eduSubjectService, name] 
    * @return: com.learn.eduservice.entity.EduSubject 
    * @Author: 
    * @Date: 2020/6/17 
    */
    private Subject existOneSubject(String name){
        QueryWrapper<Subject> wrapper=new QueryWrapper<>();
        wrapper.eq("title",name);
        wrapper.eq("parent_id","0");
        Subject oneSubject = subjectMapper.selectOne(wrapper);
        return oneSubject;
    }

    /**
    * @Description:  判断二级分类不能重复添加  根据分类名称和父id查询数据是否存在
    * @Param: [eduSubjectService, name]
    * @return: com.learn.eduservice.entity.EduSubject
    * @Author:
    * @Date: 2020/6/17
    */
    private Subject existTwoSubject(String name,String pid){
        QueryWrapper<Subject> wrapper=new QueryWrapper<>();
        wrapper.eq("title",name);
        wrapper.eq("parent_id",pid);
        Subject twoSubject = subjectMapper.selectOne(wrapper);
        return twoSubject;
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }
}
