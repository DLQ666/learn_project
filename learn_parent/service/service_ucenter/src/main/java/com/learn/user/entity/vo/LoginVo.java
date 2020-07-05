package com.learn.user.entity.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @program: learn_parent
 * @description: 登录表单字段实体类
 * @author: Hasee
 * @create: 2020-07-05 12:39
 */
@Data
public class LoginVo implements Serializable {

    private static final long serialVersionUID = 1L;
    private String mobile;
    private String password;
}
