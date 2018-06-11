package com.zhang.rpc.client;

import com.zhang.rpc.RPCRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;


/**
 * Created by zhangc on 2018/5/25.
 */
public class RPCClientProxy {
    Logger logger = LogManager.getLogger(LogManager.ROOT_LOGGER_NAME);




    public   <T> T create(Class clazz) {
        return (T) Proxy.newProxyInstance(clazz.getClassLoader(),
                new Class<?>[]{clazz},
                new ProxyHandler(clazz));
    }

    class ProxyHandler implements InvocationHandler {

        private final Class clazz;

        public ProxyHandler(Class clazz) {
            this.clazz = clazz;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

            RPCRequest rb = new RPCRequest();
            rb.setClassName(clazz.getName());
            rb.setMethodName(method.getName());
            rb.setParameterTypes(method.getParameterTypes());
            rb.setParameters(args);

            return new RPCClient().send(rb);
        }
    }
}
