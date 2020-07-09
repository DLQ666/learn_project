package com.learn.user.controller;

import com.learn.user.service.MemberService;
import com.learn.utils.result.ResponseResult;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: learn_parent
 * @description: 统计日注册数量前端控制器
 * @author: Hasee
 * @create: 2020-07-08 19:20
 */
@Api(description = "会员管理")
@Slf4j
@RestController
@RequestMapping("/admin/ucenter/member")
public class MemberController {

    @Autowired
    private MemberService memberService;

    @GetMapping("/countRegisterNum/{day}")
    public ResponseResult countRegisterNum(@PathVariable String day){
        Integer registerNum = memberService.countRegisterNum(day);
        return ResponseResult.ok().data("registerNum",registerNum);
    }
}
