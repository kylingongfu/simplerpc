package com.zhang.rpc.client;

import com.zhang.rpc.RPCResponse;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Created by zhangc on 2018/5/28.
 */
public class ClientHandler extends ChannelInboundHandlerAdapter {
    private Object objMonitor;
    Logger logger = LogManager.getLogger(ClientHandler.class);
    private  RPCResponse response;

    // private final ByteBuf firstMessage;

    /**
     * Creates a client-side handler.
     * @param response
     * @param objMonitor 用于在异步线程间控制多线程同步的中介对象
     */
    public ClientHandler(RPCResponse response, Object objMonitor) {
        /*firstMessage = Unpooled.buffer(256);
        for (int i = 0; i < firstMessage.capacity(); i ++) {
            firstMessage.writeByte((byte) i);
        }*/
        //response = new RPCResponse();
        this.response = response;
        this.objMonitor = objMonitor;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) {

       // ctx.writeAndFlush(firstMessage);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        //ctx.write(msg);
        logger.debug("client channelRead msg:"+msg);
        response.setResult(msg);
        synchronized (objMonitor){
            objMonitor.notify();
        }

    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        // Close the connection when an exception is raised.
        cause.printStackTrace();
        ctx.close();
    }
}
