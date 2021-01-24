package com.learn.statistics.service;

import com.learn.statistics.entity.Daily;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 网站统计日数据 服务类
 * </p>
 *
 * @author dlq
 * @since 2021-01-24
 */
public interface DailyService extends IService<Daily> {

    void createStatisticsByDay(String day);
}
