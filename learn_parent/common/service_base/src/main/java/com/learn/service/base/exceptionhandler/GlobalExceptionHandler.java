package com.learn.service.base.exceptionhandler;

import com.learn.utils.result.ResponseResult;
import com.learn.utils.result.ResultCodeEnum;
import com.learn.utils.utils.ExceptionUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @program: learn_parent
 * @description: 统一异常处理类
 * @author: Hasee
 * @create: 2020-06-18 21:38
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseResult error(Exception e){
        e.printStackTrace();
        log.error(ExceptionUtil.getMessage(e));
        return ResponseResult.error();
    }

    @ExceptionHandler(BadSqlGrammarException.class)
    public ResponseResult error(BadSqlGrammarException e){
        e.printStackTrace();
        log.error(ExceptionUtil.getMessage(e));
        return ResponseResult.setResult(ResultCodeEnum.BAD_SQL_GRAMMAR);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseResult error(HttpMessageNotReadableException e){
        e.printStackTrace();
        log.error(ExceptionUtil.getMessage(e));
        return ResponseResult.setResult(ResultCodeEnum.JSON_PARSE_ERROR);
    }
}
