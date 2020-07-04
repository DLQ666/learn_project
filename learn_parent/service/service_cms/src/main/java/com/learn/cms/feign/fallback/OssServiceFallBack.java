package com.learn.cms.feign.fallback;

import com.learn.cms.feign.OssService;
import com.learn.utils.result.ResponseResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @program: learn_parent
 * @description: 服务熔断  通过熔断做一个降级处理 --》远程调用接口---》本地实现类
 * @author: Hasee
 * @create: 2020-06-24 14:17
 */
@Service
@Slf4j
public class OssServiceFallBack implements OssService {

    @Override
    public ResponseResult removeFile(String url) {
        log.info("熔断保护");
        return ResponseResult.error();
    }

}
