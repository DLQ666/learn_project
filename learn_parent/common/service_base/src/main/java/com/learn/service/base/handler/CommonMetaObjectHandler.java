package com.learn.service.base.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @program: learn_parent
 * @description: 自动填充处理类
 * @author: Hasee
 * @create: 2020-06-18 21:11
 */
@Component
public class CommonMetaObjectHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        this.strictInsertFill(metaObject,"gmtCreate",Date.class,new Date());
        this.strictInsertFill(metaObject,"gmtModified",Date.class,new Date());
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        this.strictUpdateFill(metaObject,"gmtModified",Date.class,new Date());
    }
}
