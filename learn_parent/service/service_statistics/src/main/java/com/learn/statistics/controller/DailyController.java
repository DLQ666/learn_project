package com.learn.statistics.controller;


import com.learn.statistics.service.DailyService;
import com.learn.utils.result.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * <p>
 * 网站统计日数据 前端控制器
 * </p>
 *
 * @author dlq
 * @since 2020-07-08
 */
@Api(description = "统计分析管理")
@RestController
@RequestMapping("/admin/statistics/daily")
public class DailyController {

    @Autowired
    private DailyService dailyService;

    @PostMapping("/create/{day}")
    public ResponseResult createdStatisticsByDay(@PathVariable("day") String day) {
        dailyService.createStatisticsByDay(day);
        return ResponseResult.ok().message("统计数据生成成功");
    }

    @ApiOperation("展示统计记录")
    @GetMapping("/showChart/{begin}/{end}")
    public ResponseResult showChart(@PathVariable("begin") String begin,@PathVariable("end") String end) {
        Map<String, Map<String, Object>> map = dailyService.getChartData(begin,end);
        return ResponseResult.ok().data("chartData",map);
    }

}

