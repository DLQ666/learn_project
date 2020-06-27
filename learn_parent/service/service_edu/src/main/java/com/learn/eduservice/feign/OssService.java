package com.learn.eduservice.feign;

import com.learn.eduservice.feign.fallback.OssServiceFallBack;
import com.learn.utils.result.ResponseResult;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @description: 远程调用Oss接口
 * @author: Hasee
 * @create: 2020-06-24 10:31
 */
@Component
@FeignClient(value = "service-oss",fallback = OssServiceFallBack.class)
public interface OssService {

    @GetMapping("/eduoss/fileoss/test")
    ResponseResult test();

    @DeleteMapping("/eduoss/fileoss/remove")
    public ResponseResult removeFile(@RequestBody String url);
}
