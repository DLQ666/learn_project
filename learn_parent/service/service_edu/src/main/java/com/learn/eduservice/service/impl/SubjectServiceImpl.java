package com.learn.eduservice.service.impl;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.learn.eduservice.entity.Subject;
import com.learn.eduservice.entity.excel.ExcelSubjectData;
import com.learn.eduservice.entity.query.SubjectQuery;
import com.learn.eduservice.listener.SubjectExcelListener;
import com.learn.eduservice.mapper.SubjectMapper;
import com.learn.eduservice.service.SubjectService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.List;

/**
 * <p>
 * 课程科目 服务实现类
 * </p>
 *
 * @author dlq
 * @since 2020-06-18
 */
@Service
public class SubjectServiceImpl extends ServiceImpl<SubjectMapper, Subject> implements SubjectService {


    /**
     * excel文件数据批量导入接口
     * @param inputStream 文件输入流
     */
    @Override
    public void batchImport(InputStream inputStream) {
        EasyExcel.read(inputStream, ExcelSubjectData.class,new SubjectExcelListener(baseMapper)).excelType(ExcelTypeEnum.XLS).sheet().doRead();
    }

    @Cacheable(value = "index",key = "'subjectList'")
    @Override
    public List<SubjectQuery> subjectList() {
        return baseMapper.selectNestedListByParentId("0");
    }
}
