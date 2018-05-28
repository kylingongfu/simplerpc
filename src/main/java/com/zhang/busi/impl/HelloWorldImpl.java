package com.zhang.busi.impl;

import com.zhang.busi.HelloWorld;
import com.zhang.rpc.server.RPCService;

import java.util.Arrays;
import java.util.List;

/**
 * Created by zhangc on 2018/5/28.
 */
@RPCService("HelloWorld")
public class HelloWorldImpl implements HelloWorld{
    @Override
    public String sayHi() {
        return "hello my world.";
    }

    @Override
    public List getNames() {
        return Arrays.asList("Tom","Jerry","David");
    }
}
