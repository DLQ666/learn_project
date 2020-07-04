package com.learn.user.entity.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @program: learn_parent
 * @description: 注册表单数据实体类
 * @author: Hasee
 * @create: 2020-07-04 17:19
 */
@Data
public class RegisterVo implements Serializable {
    private static final long serialVersionUID = 1L;
    private String nickname;
    private String mobile;
    private String password;
    private String code;
}
