package com.learn.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.learn.service.base.dto.MemberDto;
import com.learn.service.base.exception.CustomException;
import com.learn.user.entity.Member;
import com.learn.user.entity.vo.LoginVo;
import com.learn.user.entity.vo.RegisterVo;
import com.learn.user.mapper.MemberMapper;
import com.learn.user.service.MemberService;
import com.learn.utils.result.ResultCodeEnum;
import com.learn.utils.utils.FormUtils;
import com.learn.utils.utils.JwtInfo;
import com.learn.utils.utils.JwtUtils;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * <p>
 * 会员表 服务实现类
 * </p>
 *
 * @author dlq
 * @since 2020-07-04
 */
@Service
public class MemberServiceImpl extends ServiceImpl<MemberMapper, Member> implements MemberService {

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public void register(RegisterVo registerVo) {
        //校验参数
        String nickname = registerVo.getNickname();
        String mobile = registerVo.getMobile();
        String password = registerVo.getPassword();
        String code = registerVo.getCode();
        if (StringUtils.isEmpty(mobile) || !FormUtils.isMobile(mobile)) {
            throw new CustomException(ResultCodeEnum.LOGIN_MOBILE_ERROR);
        }
        if (StringUtils.isEmpty(nickname) || StringUtils.isEmpty(password) || StringUtils.isEmpty(code)) {
            throw new CustomException(ResultCodeEnum.PARAM_ERROR);
        }

        //校验验证码
        String checkCode = (String) redisTemplate.opsForValue().get(mobile);
        //此处注意：应将从redis中获取的checkCode放在比较的后边，上方已经判断code不为空，
        // 所以如果将checkCode放前边与code作比较。会出现空指针异常，因为如果redis中数据过期后会出现  空.equals-》与 code作比较--》空指针异常
        if (!code.equals(checkCode)) {
            throw new CustomException(ResultCodeEnum.CODE_ERROR);
        }

        //先校检验证码的目的：首先校检验证码是从redis中查验证码信息的，而校检用户是否注册是从mysql数据库查数据的
        //如果先校检用户是否注册，会先查mysql数据库，然后校检通过，再次校检验证码，此时验证码查不到，或者过期了，那么先前验证的一次用户是否注册
        //就会浪费mysql数据库资源，如果先验证验证码，则验证码错误后，就不会去校检用户是否注册，会节省mysql访问资源，两个都通过就注册成功。
        //校检用户是否注册：mysql
        QueryWrapper<Member> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("mobile", mobile);
        Integer count = baseMapper.selectCount(queryWrapper);
        if (count > 0) {
            throw new CustomException(ResultCodeEnum.REGISTER_MOBLE_ERROR);
        }
        //注册
        Member member = new Member();
        member.setNickname(nickname);
        member.setMobile(mobile);
        PasswordEncoder passwordEncoder=new BCryptPasswordEncoder();
        member.setPassword(passwordEncoder.encode(password));
        member.setAvatar("https://dlqedu-01.oss-cn-beijing.aliyuncs.com/avatar/2020/06/27/571cfd8f-679b-4174-92c6-e109b30c2583.png");
        member.setDisabled(false);
        baseMapper.insert(member);
    }

    @Override
    public String login(LoginVo loginVo) {
        String mobile = loginVo.getMobile();
        String password = loginVo.getPassword();
        //校验：参数是否合法
        if (StringUtils.isEmpty(mobile) || !FormUtils.isMobile(mobile) ||StringUtils.isEmpty(password)) {
            throw new CustomException(ResultCodeEnum.PARAM_ERROR);
        }

        //校验手机号是否存在
        QueryWrapper<Member> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("mobile",mobile);
        Member member = baseMapper.selectOne(queryWrapper);
        if (member == null){
            throw new CustomException(ResultCodeEnum.LOGIN_MOBILE_ERROR);
        }

        //校验密码是否正确
        PasswordEncoder bCryptPasswordEncoder=new BCryptPasswordEncoder();
        if (!bCryptPasswordEncoder.matches(password,member.getPassword())){
            throw new CustomException(ResultCodeEnum.LOGIN_PASSWORD_ERROR);
        }
        //校验用户是否被禁用
        if (member.getDisabled()){
            throw new CustomException(ResultCodeEnum.LOGIN_DISABLED_ERROR);
        }

        //登录：生成token
        JwtInfo jwtInfo = new JwtInfo();
        jwtInfo.setId(member.getId());
        jwtInfo.setNickname(member.getNickname());
        jwtInfo.setAvatar(member.getAvatar());

        String jwtToken = JwtUtils.getJwtToken(jwtInfo, 1800);
        return jwtToken;
    }

    @Override
    public Member getByOpenId(String openid) {

        QueryWrapper<Member> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("openid",openid);
        return baseMapper.selectOne(queryWrapper);
    }

    @Override
    public MemberDto getMemberDtoByMemberId(String memberId) {
        Member member = baseMapper.selectById(memberId);
        MemberDto memberDto = new MemberDto();
        BeanUtils.copyProperties(member,memberDto);
        return memberDto;
    }
}
