package com.learn.eduservice.feign.fallback;

import com.learn.eduservice.feign.VodService;
import com.learn.utils.result.ResponseResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @program: learn_parent
 * @description: Vod远程调用服务 容错类  服务熔断  通过熔断做一个降级处理 --》远程调用接口---》本地实现类
 * @author: Hasee
 * @create: 2020-07-01 11:39
 */
@Service
@Slf4j
public class VodServiceFallBack implements VodService {

    @Override
    public ResponseResult removeVideo(String vodId) {
        log.info("熔断保护");
        return ResponseResult.error();
    }

    @Override
    public ResponseResult removeVideoByIdList(List<String> videoIdList) {
        log.info("熔断保护");
        return ResponseResult.error();
    }
}
