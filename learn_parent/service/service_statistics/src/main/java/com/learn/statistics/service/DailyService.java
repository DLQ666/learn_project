package com.learn.statistics.service;

import com.learn.statistics.entity.Daily;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 网站统计日数据 服务类
 * </p>
 *
 * @author dlq
 * @since 2020-07-08
 */
public interface DailyService extends IService<Daily> {

    /**
     * 统计远程调用用户中心注册数接口的结果保存到数据中心数据库
     * @param day 统计日期
     */
    void createStatisticsByDay(String day);

    /**
     * 展示统计数据
     * @param begin 开始时间
     * @param end 结束时间
     * @return 数据列表
     */
    Map<String, Map<String, Object>> getChartData(String begin, String end);

}
