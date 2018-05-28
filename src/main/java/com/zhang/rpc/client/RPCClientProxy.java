package com.zhang.rpc.client;

import com.zhang.rpc.RPCRequest;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Created by zhangc on 2018/5/25.
 */
public class RPCClientProxy {
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
            rb.setClassName(clazz.getTypeName());
            rb.setParameterTypes(method.getParameterTypes());
            rb.setParameters(args);

            return new RPCClient().send(rb);
        }
    }
}
