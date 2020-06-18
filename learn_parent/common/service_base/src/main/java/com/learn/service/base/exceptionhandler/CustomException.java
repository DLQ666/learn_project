package com.learn.service.base.exceptionhandler;

import com.learn.utils.result.ResponseResult;
import com.learn.utils.result.ResultCodeEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @program: learn_parent
 * @description: 自定义常处理类
 * @author: Hasee
 * @create: 2020-06-18 18:40
 * @AllArgsConstructor: 生成有参数构造方法
 * @NoArgsConstructor: 生成无参数构造
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomException extends RuntimeException {
    /**
     * 状态码
     */
    private Integer code;

    /**
     * 异常信息
     */
    private String msg;
}
