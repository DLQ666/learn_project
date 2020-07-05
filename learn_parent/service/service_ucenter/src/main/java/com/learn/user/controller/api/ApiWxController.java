package com.learn.user.controller.api;

import com.google.gson.Gson;
import com.learn.service.base.exception.CustomException;
import com.learn.user.entity.Member;
import com.learn.user.service.MemberService;
import com.learn.user.util.UcenterProperties;
import com.learn.utils.result.ResultCodeEnum;
import com.learn.utils.utils.ExceptionUtil;
import com.learn.utils.utils.HttpClientUtils;
import com.learn.utils.utils.JwtInfo;
import com.learn.utils.utils.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.ParseException;
import java.util.HashMap;
import java.util.UUID;

/**
 * @program: learn_parent
 * @description: 微信登录获取二维码URl前端控制器
 * @author: Hasee
 * @create: 2020-07-05 16:49
 */
@Slf4j
@CrossOrigin
@Controller//注意这里没有配置 @RestController
@RequestMapping("/api/ucenter/wx")
public class ApiWxController {

    @Autowired
    private UcenterProperties ucenterProperties;
    @Autowired
    private MemberService memberService;

    @GetMapping("/login")
    public String genQrConnect(HttpSession httpSession) {
        //组装二维码url
        String baseUrl = "https://open.weixin.qq.com/connect/qrconnect" +
                "?appid=%s" +
                "&redirect_uri=%s" +
                "&response_type=code" +
                "&scope=snsapi_login" +
                "&state=%s" +
                "#wechat_redirect";

        //将回调URL进行转码
        String redirectUri = "";
        try {
            redirectUri = URLEncoder.encode(ucenterProperties.getRedirectUri(), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            log.error(ExceptionUtil.getMessage(e));
            throw new CustomException(ResultCodeEnum.URL_ENCODE_ERROR);
        }
        //生成随机state，防止csrf击攻
        String state = UUID.randomUUID().toString();
        //将state存入session
        httpSession.setAttribute("wx_open_state", state);

        String qrcodeUrl = String.format(baseUrl, ucenterProperties.getAppId(), redirectUri, state);
        //跳转到组装的url地址中去
        return "redirect:" + qrcodeUrl;
    }

    @GetMapping("/callback")
    public String callBack(String code, String state, HttpSession session) {
        log.info("callback被调用");
        log.info("code:" + code);
        log.info("state:" + state);
        if (StringUtils.isEmpty(code) || StringUtils.isEmpty(state)) {
            log.error("非法回调请求");
            throw new CustomException(ResultCodeEnum.ILLEGAL_CALLBACK_REQUEST_ERROR);
        }

        //从redis中的session中拿state
        String sessionState = (String) session.getAttribute("wx_open_state");
        if (!state.equals(sessionState)) {
            log.error("非法回调请求");
            throw new CustomException(ResultCodeEnum.ILLEGAL_CALLBACK_REQUEST_ERROR);
        }

        //https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code
        //携带code临时票据，和appid以及appsecrent请求access_token和openid（微信唯一标识）
        String accessTokenUrl = "https://api.weixin.qq.com/sns/oauth2/access_token";
        //组装参数?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code
        HashMap<String, String> accessTokenParam = new HashMap<>();
        accessTokenParam.put("appid", ucenterProperties.getAppId());
        accessTokenParam.put("secret", ucenterProperties.getAppSecret());
        accessTokenParam.put("code", code);
        accessTokenParam.put("grant_type", "authorization_code");
        HttpClientUtils client = new HttpClientUtils(accessTokenUrl, accessTokenParam);

        String result = "";
        try {
            //发送请求：组装完整的url字符串、发送请求
            client.get();
            //得到响应
            result = client.getContent();
            log.info("result = " + result);
        } catch (Exception e) {
            log.error("获取access_token失败");
            throw new CustomException(ResultCodeEnum.FETCH_ACCESSTOKEN_FAILD);
        }

        Gson gson = new Gson();
        HashMap<String, Object> resultMap = gson.fromJson(result, HashMap.class);

        //失败的相应结果
        Object errcode = resultMap.get("errcode");
        if (errcode != null) {
            Double errorcode = (Double) errcode;
            String errormsg = (String) resultMap.get("errmsg");
            log.error("获取access_token失败" + "code: " + errorcode + ", message:" + errormsg);
            throw new CustomException(ResultCodeEnum.FETCH_ACCESSTOKEN_FAILD);
        }

        //解析出结果中的access_token和openid
        String accessToken = (String) resultMap.get("access_token");
        String openid = (String) resultMap.get("openid");

        log.info("accessToken:" + accessToken);
        log.info("openid:" + openid);

        //在本地数据库中查找当前微信用户信息
        Member member = memberService.getByOpenId(openid);
        //if：如果当前用户不存在，
        if (member == null) {
            //则去微信的资源服务器获取用户个人信息（携带access_token）
            String baseUserInfoUrl = "https://api.weixin.qq.com/sns/userinfo";
            //组装参数?access_token=ACCESS_TOKEN&openid=OPENID
            HashMap<String, String> baseUserInfoParam = new HashMap<>();
            baseUserInfoParam.put("access_token", accessToken);
            baseUserInfoParam.put("openid", openid);
            client = new HttpClientUtils(baseUserInfoUrl, baseUserInfoParam);

            String resultUserInfo = "";
            try {
                client.get();
                resultUserInfo = client.getContent();
            } catch (Exception e) {
                log.error(ExceptionUtil.getMessage(e));
                throw new CustomException(ResultCodeEnum.FETCH_USERINFO_ERROR);
            }

            HashMap<String, Object> resultUserInfoMap = gson.fromJson(resultUserInfo, HashMap.class);
            //失败的相应结果
            errcode = resultUserInfoMap.get("errcode");
            if (errcode != null) {
                Double errorcode = (Double) errcode;
                String errormsg = (String) resultMap.get("errmsg");
                log.error("获取用户信息失败" + "code: " + errorcode + ", message:" + errormsg);
                throw new CustomException(ResultCodeEnum.FETCH_USERINFO_ERROR);
            }

            //解析出结果中的用户个人信息
            String nickname = (String) resultUserInfoMap.get("nickname");
            String avatar = (String) resultUserInfoMap.get("headimgurl");
            Double sex = (Double) resultUserInfoMap.get("sex");

            //在本地数据库中插入当前微信用户的信息（使用微信账号在本地服务注册新用户）
            member = new Member();
            member.setOpenid(openid);
            member.setNickname(nickname);
            member.setSex(sex.intValue());
            member.setAvatar(avatar);
            memberService.save(member);
        }

        //如果当前用户已经存在，则直接使用当前用户的信息登录（生成jwt的过程）
        //member=>jwt
        JwtInfo jwtInfo = new JwtInfo();
        jwtInfo.setId(member.getId());
        jwtInfo.setNickname(member.getNickname());
        jwtInfo.setAvatar(member.getAvatar());
        String jwtToken = JwtUtils.getJwtToken(jwtInfo, 1800);

        return "redirect:http://localhost:3000?token=" + jwtToken;
    }
}
