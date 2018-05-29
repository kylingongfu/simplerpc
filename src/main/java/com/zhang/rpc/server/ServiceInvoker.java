package com.zhang.rpc.server;

import com.zhang.rpc.RPCRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * Created by zhangc on 2018/5/25.
 * 调用业务类的处理类
 */


public class ServiceInvoker {
    Logger logger = LogManager.getLogger(ServiceInvoker.class);
    private final Map objMap;

    public ServiceInvoker(Map objMap) {
        this.objMap = objMap;
    }

    public Object invoke(RPCRequest rb) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        logger.debug(rb);
        Object obj = objMap.get(rb.getClassName());
        Class<?> clazz = Class.forName(rb.getClassName());
        Method method = clazz.getMethod(rb.getMethodName(), rb.getParameterTypes());
        return method.invoke(obj, rb.getParameters());
    }


}
