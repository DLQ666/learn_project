package com.learn.cms.controller.api;

import com.baomidou.mybatisplus.extension.api.R;
import com.learn.cms.entity.Ad;
import com.learn.cms.service.AdService;
import com.learn.utils.result.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @program: learn_parent
 * @description: 门户广告查询前端控制器类
 * @author: Hasee
 * @create: 2020-07-03 17:12
 */
@Api(description = "广告推荐")
@CrossOrigin
@RestController
@RequestMapping("/api/cms/ad")
public class ApiAdController {

    @Autowired
    private AdService adService;
    @Autowired
    private RedisTemplate redisTemplate;

    @ApiOperation("根据推荐位id显示广告推荐")
    @GetMapping("/list/{adTypeId}")
    public ResponseResult listByAdTypeId(@ApiParam(value = "推荐位id", required = true) @PathVariable String adTypeId) {
        List<Ad> adList = adService.selectByAdTypeId(adTypeId);
        return ResponseResult.ok().data("items",adList);
    }

    @PostMapping("/saveTest")
    public ResponseResult saveAd(@RequestBody Ad ad){
        redisTemplate.opsForValue().set("ad",ad);
        return ResponseResult.ok();
    }

    @GetMapping("/getTest/{key}")
    public ResponseResult getAd(@PathVariable("key") String key){
        Ad ad = (Ad) redisTemplate.opsForValue().get(key);
        return ResponseResult.ok().data("ad",ad);
    }

    @DeleteMapping("/getTest/{key}")
    public ResponseResult deleteAd(@PathVariable("key") String key){
        Boolean delete = redisTemplate.delete(key);
        System.out.println(delete);
        Boolean aBoolean = redisTemplate.hasKey(key);
        System.out.println(aBoolean);
        return ResponseResult.ok();
    }
}
