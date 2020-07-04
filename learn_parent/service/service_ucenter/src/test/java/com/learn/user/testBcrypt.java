package com.learn.user;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @program: learn_parent
 * @description: 密码加密测试用例
 * @author: Hasee
 * @create: 2020-07-04 18:34
 */
public class testBcrypt {

    @Test
    public void testPasswordEncoder(){
        String password="111111";
        PasswordEncoder passwordEncoder=new BCryptPasswordEncoder();
        for (int i=0;i<10;i++){
            //每个计算出的Hash值都不一样
            String hashPass=passwordEncoder.encode(password);
            System.out.println(hashPass);

            //虽然每次计算的密码hash值是不一样的但是校检是通过的
            boolean f=passwordEncoder.matches(password,hashPass);
            System.out.println(f);
        }
    }
}
