package com.learn.statistics.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.learn.statistics.entity.Daily;
import com.learn.statistics.feign.MemberService;
import com.learn.statistics.mapper.DailyMapper;
import com.learn.statistics.service.DailyService;
import com.learn.utils.result.ResponseResult;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 网站统计日数据 服务实现类
 * </p>
 *
 * @author dlq
 * @since 2021-01-24
 */
@Service
public class DailyServiceImpl extends ServiceImpl<DailyMapper, Daily> implements DailyService {

    @Autowired
    private MemberService memberService;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void createStatisticsByDay(String day) {

        //如果当前日志统计信息已存在，则删除记录
        QueryWrapper<Daily> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("date_calculated", day);
        baseMapper.delete(queryWrapper);

        //远程获取了注册用户数据的统计结果
        ResponseResult r = memberService.countRegisterNum(day);
        Integer registerNum = (Integer) r.getData().get("registerNum");
        int loginNum = RandomUtils.nextInt(100, 200);
        int videoViewNum = RandomUtils.nextInt(100, 200);
        int courseNum = RandomUtils.nextInt(100, 200);
        Daily daily = new Daily();
        daily.setLoginNum(loginNum);
        daily.setCourseNum(courseNum);
        daily.setRegisterNum(registerNum);
        daily.setVideoViewNum(videoViewNum);
        daily.setDateCalculated(day);

        baseMapper.insert(daily);
    }
}
