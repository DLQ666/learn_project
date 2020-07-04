package com.learn.cms.feign;

import com.learn.cms.feign.fallback.OssServiceFallBack;
import com.learn.utils.result.ResponseResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @program: learn_parent
 * @description: 远程调用Oss服务接口
 * @author: Hasee
 * @create: 2020-07-03 16:36
 */
@Service
@FeignClient(value = "service-oss", fallback = OssServiceFallBack.class)
public interface OssService {

    @DeleteMapping("/eduoss/fileoss/remove")
    public ResponseResult removeFile(@RequestBody String url);
}
