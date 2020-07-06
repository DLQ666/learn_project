package com.learn.user.service;

import com.learn.service.base.dto.MemberDto;
import com.learn.user.entity.Member;
import com.baomidou.mybatisplus.extension.service.IService;
import com.learn.user.entity.vo.LoginVo;
import com.learn.user.entity.vo.RegisterVo;

/**
 * <p>
 * 会员表 服务类
 * </p>
 *
 * @author dlq
 * @since 2020-07-04
 */
public interface MemberService extends IService<Member> {

    /**
     * 用户注册接口
     * @param registerVo 注册表单对象
     */
    void register(RegisterVo registerVo);

    /**
     * 用户登录接口
     * @param loginVo 登陆表单对象
     * @return 生成的token
     */
    String login(LoginVo loginVo);

    /**
     * 根据微信用户openid查询用户
     * @param openid 微信用户openid
     * @return 微信用户
     */
    Member getByOpenId(String openid);

    /**
     * 根据id查询会员信息--->n内部远程调用接口
     * @param memberId 会员id
     * @return 会员实体
     */
    MemberDto getMemberDtoByMemberId(String memberId);
}
