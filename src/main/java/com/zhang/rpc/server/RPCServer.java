package com.zhang.rpc.server;

import com.zhang.rpc.server.netty.ServerNetty;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by zhangc on 2018/5/24.
 */
public class RPCServer implements ApplicationContextAware, InitializingBean {
    Logger logger = LogManager.getLogger(RPCServer.class);
    private ApplicationContext applicationContex;


    @Override
    public void afterPropertiesSet() throws Exception {
        startServer();
    }

    private void startServer() {
        Map objMap = loadBusiBeans();
        RPCServerContext sc = RPCServerContext.getInstance()
                .setPort(8888)
                //.setServiceBean(objMap)
                .setServiceInvoker(new ServiceInvoker(objMap));
        try {
            //启动netty服务端
            new ServerNetty(sc).ready();
        } catch (Exception e) {
            e.printStackTrace();
        }
        login2Resitry();
    }

    private void login2Resitry() {
    }

    /**
     * 将rpc注解标注的业务类存放到容器中
     */
    private Map<String, Object> loadBusiBeans() {
        Map<String, Object> beanMap = applicationContex.getBeansWithAnnotation(RPCService.class);
        Map<String, Object> objectMap = new HashMap<>();
        for (Object bean:beanMap.values()){
            String interfaceName = bean.getClass().getAnnotation(RPCService.class).value().getName();
            objectMap.put(interfaceName,bean);
        }
        logger.debug("load busi service beans :"+objectMap);

        return objectMap;
    }


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContex = applicationContext;
    }

    public static void main(String[] args) {
        //ApplicationContext context = new ClassPathXmlApplicationContext("services.xml", "daos.xml");
        ApplicationContext context = new ClassPathXmlApplicationContext("rpcserver.xml");
    }
}
