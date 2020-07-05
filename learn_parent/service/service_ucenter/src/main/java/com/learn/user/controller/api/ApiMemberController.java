package com.learn.user.controller.api;


import com.baomidou.mybatisplus.extension.api.R;
import com.learn.service.base.exception.CustomException;
import com.learn.user.entity.vo.LoginVo;
import com.learn.user.entity.vo.RegisterVo;
import com.learn.user.service.MemberService;
import com.learn.utils.result.ResponseResult;
import com.learn.utils.result.ResultCodeEnum;
import com.learn.utils.utils.JwtInfo;
import com.learn.utils.utils.JwtUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 会员表 前端控制器
 * </p>
 *
 * @author dlq
 * @since 2020-07-04
 */
@Api(description = "会员管理")
@Slf4j
@CrossOrigin
@RestController
@RequestMapping("/api/ucenter/member")
public class ApiMemberController {

    @Autowired
    private MemberService memberService;

    @ApiOperation(value = "会员注册")
    @PostMapping("/register")
    public ResponseResult register(@RequestBody RegisterVo registerVo){
        memberService.register(registerVo);
        return ResponseResult.ok().message("注册成功");
    }

    @ApiOperation(value = "会员登录")
    @PostMapping("/login")
    public ResponseResult Login(@RequestBody LoginVo loginVo){
        String token = memberService.login(loginVo);
        return ResponseResult.ok().data("token",token).message("登录成功");
    }

    @ApiOperation(value = "根据token获取登录信息")
    @GetMapping("get-login-info")
    public ResponseResult getLoginInfo(HttpServletRequest request){

        try {
            JwtInfo jwtInfo = JwtUtils.getMemberIdByJwtToken(request);
            return ResponseResult.ok().data("userInfo",jwtInfo);
        } catch (Exception e) {
            log.error("解析用户信息失败："+e.getMessage());
            throw new CustomException(ResultCodeEnum.FETCH_USERINFO_ERROR);
        }
    }

}

