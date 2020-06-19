package com.learn.eduservice.controller;

import com.learn.utils.result.ResponseResult;
import org.springframework.web.bind.annotation.*;

/**
 * @program: learn_parent
 * @description: 模拟登录类---后期加入springsecurity再改回正式登录
 * @author: Hasee
 * @create: 2020-06-15 21:16
 * @CrossOrigin: 解决跨域
 */
@CrossOrigin
@RestController
@RequestMapping("/eduservice/user")
public class LoginController {

    /**
     * 登录
     */
    @PostMapping("/login")
    public ResponseResult login(){
        return ResponseResult.ok().data("token","admin");
    }

    /**
     * 获取用户信息
     */
    @GetMapping("/info")
    public ResponseResult info(){
        return ResponseResult.ok().data("roles","[admin]").data("name","admin").data("avatar","https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
    }

    /**
     *
     */
    @PostMapping("/logout")
    public ResponseResult logout(){
        return ResponseResult.ok();
    }
}
