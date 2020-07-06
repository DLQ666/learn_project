package com.learn.service.base.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @program: learn_parent
 * @description: 公共数据传输实体类  ->（会员信息dto实体类）
 * @author: Hasee
 * @create: 2020-07-06 13:04
 */
@Data
public class MemberDto  implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;//会员id
    private String mobile;//手机号
    private String nickname;//昵称
}
