package com.learn.statistics.task;

import com.learn.statistics.service.DailyService;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @program: learn_parent
 * @description: 任务调度类
 * @author: Hasee
 * @create: 2020-07-08 20:33
 */
@Slf4j
@Component
public class ScheduledTask {

    @Autowired
    private DailyService dailyService;

    /*@Scheduled(cron = "0/3 * * * * *")
    public void task1(){
        log.info("task1 在执行...........");
    }*/

    @Scheduled(cron = "0 0 1 * * ?")
    public void taskGenStatisticsData(){
        log.info("task1 在执行...........");
        String day = new DateTime().minusDays(1).toString("yyyy-MM-dd");
        dailyService.createStatisticsByDay(day);
    }
}
