package com.zhang.rpc.server;

import java.util.Map;

/**
 * Created by zhangc on 2018/5/25.
 */
public class RPCServerContext{

    private ServiceInvoker si;
    private int port;
    private Map<String,Object> serviceBean;

    public Map<String, Object> getServiceBean() {
        return serviceBean;
    }

    public RPCServerContext setServiceBean(Map<String, Object> serviceBean) {
        sc.serviceBean = serviceBean;
        return sc;
    }

    //private T serviceBean;
    private static RPCServerContext sc = new RPCServerContext();

    private RPCServerContext(){}

    public static RPCServerContext getInstance(){
        return sc;
    }

    public ServiceInvoker getServiceInvoker() {
        return si;
    }

    public RPCServerContext setServiceInvoker(ServiceInvoker si) {
        sc.si = si;
        return sc;
    }

    public int getPort() {
        return sc.port;
    }

    public RPCServerContext setPort(int port) {
        sc.port = port;
        return sc;
    }

   /* public RPCServerContext setServiceBean(T serviceBean) {
        sc.serviceBean = serviceBean;
        return sc;
    }*/
}
