package com.zhang.rpc;

/**
 * 实体类，报文的封装类，用于网络消息传输
 * Created by zhangc on 2018/5/25.
 */
public class RPCRequest {
    private String className;

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    private String methodName;
    private Class<?>[] parameterTypes;
    private Object[] parameters;

    public Object[] getParameters() {
        return parameters;
    }

    public void setParameters(Object[] parameters) {
        this.parameters = parameters;
    }

    public Class<?>[] getParameterTypes() {
        return parameterTypes;
    }

    public void setParameterTypes(Class<?>[] parameters) {
        this.parameterTypes = parameters;
    }


    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }
}
