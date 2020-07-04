package com.learn.eduservice.service;

import com.learn.eduservice.entity.Subject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.learn.eduservice.entity.query.SubjectQuery;

import java.io.InputStream;
import java.util.List;

/**
 * <p>
 * 课程科目 服务类
 * </p>
 *
 * @author dlq
 * @since 2020-06-18
 */
public interface SubjectService extends IService<Subject> {

    /**
     * excel文件数据批量导入接口
     * @param inputStream 文件输入流
     */
    void batchImport(InputStream inputStream);

    /**
     * 查询课程分类列表
     * @return 课程分类列表
     */
    List<SubjectQuery> subjectList();

}
