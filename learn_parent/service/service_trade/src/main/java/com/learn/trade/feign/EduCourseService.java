package com.learn.trade.feign;

import com.learn.service.base.dto.CourseDto;
import com.learn.trade.feign.fallback.EduCourseServiceFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @description: 远程调用edu课程信息微服务接口
 * @author: Hasee
 * @create: 2020-07-06 13:54
 */
@Component
@FeignClient(value = "service-edu",fallback = EduCourseServiceFallback.class)
public interface EduCourseService {

    @GetMapping("/api/edu/course/inner/getCourseDto/{courseId}")
    public CourseDto getCourseDtoById(@PathVariable("courseId") String courseId);
}
