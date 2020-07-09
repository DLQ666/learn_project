package com.learn.trade.feign.fallback;

import com.learn.service.base.dto.CourseDto;
import com.learn.trade.feign.EduCourseService;
import com.learn.utils.result.ResponseResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @program: learn_parent
 * @description: 远程调用熔断类
 * @author: Hasee
 * @create: 2020-07-06 14:20
 */
@Slf4j
@Service
public class EduCourseServiceFallback implements EduCourseService {
    @Override
    public CourseDto getCourseDtoById(String courseId) {
        log.info("熔断保护");
        return null;
    }

    @Override
    public ResponseResult updateBuyCountById(String id) {
        log.info("熔断保护");
        return ResponseResult.error();
    }
}
