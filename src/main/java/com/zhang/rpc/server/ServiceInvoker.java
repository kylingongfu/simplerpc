package com.zhang.rpc.server;

import com.zhang.rpc.RPCRequest;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * Created by zhangc on 2018/5/25.
 * 调用业务类的处理类
 */


public class ServiceInvoker {
    private final Map objMap;

    public ServiceInvoker(Map objMap) {
        this.objMap = objMap;
    }

    public Object invoke(RPCRequest rb) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Object obj = objMap.get(rb.getClassName());
        Class<?> clazz = Class.forName(rb.getClassName());
        Method method = clazz.getMethod(rb.getMethodName(), rb.getParameterTypes());
        return method.invoke(obj, rb.getParameters());
    }


}
