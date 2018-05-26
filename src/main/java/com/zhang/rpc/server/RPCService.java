package com.zhang.rpc.server;

import org.springframework.stereotype.Component;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by zhangc on 2018/5/24.
 */
//标注在类上
@Target({ElementType.TYPE})

//运行时生效——反射时需要
@Retention(RetentionPolicy.RUNTIME)
@Component
public @interface RPCService {
    String value();
}
