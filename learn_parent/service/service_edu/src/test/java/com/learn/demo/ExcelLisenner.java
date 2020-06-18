package com.learn.demo;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.learn.demo.excel.DemoData;

import java.util.Map;

/**
 * @program: learn_parent
 * @description: 测试读Excel监听器类
 * @author: Hasee
 * @create: 2020-06-17 15:38
 */
public class ExcelLisenner extends AnalysisEventListener<DemoData> {

    //一行一行读取Excel内容
    @Override
    public void invoke(DemoData demoData, AnalysisContext analysisContext) {
        System.out.println("****" + demoData);
    }

    //读取表头
    @Override
    public void invokeHeadMap(Map<Integer, String> headMap, AnalysisContext context) {
        System.out.println("表头：" + headMap);
    }

    //读取完成后
    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }
}
