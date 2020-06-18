package com.learn.demo;

import com.alibaba.excel.EasyExcel;
import com.learn.demo.excel.DemoData;

import java.util.ArrayList;
import java.util.List;

/**
 * @program: learn_parent
 * @description: 测试EasyExcel测试类
 * @author: Hasee
 * @create: 2020-06-17 15:24
 */
public class testEasyExcel {

    public static void main(String[] args) {
   /*     //实现Excel写操作
        //1、设置写入文件夹地址和Excel文件名称
        String filename="F:/write.xlsx";
        //2、调用EasyExcel里面的方法实现写操作
        //write方法两个参数：第一个参数文件路径名称，第二个参数 实体类class
        EasyExcel.write(filename, DemoData.class).sheet("学生列表").doWrite(getData());*/

        //实现EasyExcel读操作
        String filename="F:/write.xlsx";
        EasyExcel.read(filename,DemoData.class,new ExcelLisenner()).sheet().doRead();
    }

    //创建一个方法返回list集合
    private static List<DemoData> getData(){
        List<DemoData> list=new ArrayList<>();
        for (int i=0;i<10;i++){
            DemoData demoData=new DemoData();
            demoData.setSno(i);
            demoData.setSname("aaa"+i);
            list.add(demoData);
        }
        return list;
    }
}
