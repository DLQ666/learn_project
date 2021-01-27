package com.learn.statistics.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.learn.statistics.entity.Daily;
import com.learn.statistics.feign.MemberService;
import com.learn.statistics.mapper.DailyMapper;
import com.learn.statistics.service.DailyService;
import com.learn.utils.result.ResponseResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 网站统计日数据 服务实现类
 * </p>
 *
 * @author dlq
 * @since 2020-07-08
 */
@Slf4j
@Service
public class DailyServiceImpl extends ServiceImpl<DailyMapper, Daily> implements DailyService {

    @Autowired
    private MemberService memberService;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void createStatisticsByDay(String day) {

        //生成之前先查询统计数据库看是否存在，存在则删除，重新生成
        QueryWrapper<Daily> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("date_calculated",day);
        baseMapper.delete(queryWrapper);

        //远程获取了注册用户数的统计结果
        ResponseResult responseResult = memberService.countRegisterNum(day);
        Integer registerNum = (Integer) responseResult.getData().get("registerNum");

        int loginNum = RandomUtils.nextInt(100, 200);

        int videoViewNum = RandomUtils.nextInt(100, 200);
        int courseNum = RandomUtils.nextInt(100, 200);

        Daily daily = new Daily();
        daily.setRegisterNum(registerNum);
        daily.setLoginNum(loginNum);
        daily.setVideoViewNum(videoViewNum);
        daily.setCourseNum(courseNum);
        daily.setDateCalculated(day);

        baseMapper.insert(daily);
    }

    @Override
    public Map<String, Map<String, Object>> getChartData(String begin, String end) {
        //查4个数
        //学员登录数统计
        Map<String, Object> registerNum = this.getChartDataByType(begin, end, "register_num");
        //学院注册数统计
        Map<String, Object> loginNum = this.getChartDataByType(begin, end, "login_num");
        //课程播放数统计
        Map<String, Object> videoViewNum = this.getChartDataByType(begin, end, "video_view_num");
        //每日新增课程数统计
        Map<String, Object> courseNum = this.getChartDataByType(begin, end, "course_num");

        Map<String, Map<String, Object>> map = new HashMap<>();
        map.put("registerNum",registerNum);
        map.put("loginNum",loginNum);
        map.put("videoViewNum",videoViewNum);
        map.put("courseNum",courseNum);
        return map;
    }


    /**
     * 根据时间和要查询的列查询数据
     * @param begin 开始时间
     * @param end 结束时间
     * @param type 要查询的列名
     * @return 数据集合
     */
    private Map<String,Object> getChartDataByType(String begin, String end,String type){

        Map<String,Object> map = new HashMap<>();


        List<String> xList = new ArrayList<>();//日期列表
        List<Integer> yList = new ArrayList<>();//数据列表

        QueryWrapper<Daily> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("date_calculated",type);
        queryWrapper.between("date_calculated",begin,end);

        List<Map<String, Object>> mapsData = baseMapper.selectMaps(queryWrapper);
        for (Map<String, Object> data : mapsData) {
            String dateCalculated = (String) data.get("date_calculated");
            xList.add(dateCalculated);

            Integer count = (Integer) data.get(type);
            yList.add(count);
        }

        log.info("sss"+mapsData);

        map.put("xData",xList);
        map.put("yData",yList);
        return map;
    }
}
