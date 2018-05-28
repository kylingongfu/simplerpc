package com.zhang.rpc.client.com.zhang.rpc.client;

import com.zhang.rpc.RPCResponse;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * Created by zhangc on 2018/5/28.
 */
public class ClientHandler extends ChannelInboundHandlerAdapter {
    private  RPCResponse response;

    // private final ByteBuf firstMessage;

    /**
     * Creates a client-side handler.
     * @param response
     */
    public ClientHandler(RPCResponse response) {
        /*firstMessage = Unpooled.buffer(256);
        for (int i = 0; i < firstMessage.capacity(); i ++) {
            firstMessage.writeByte((byte) i);
        }*/
        response = new RPCResponse();
        this.response = response;

    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) {

       // ctx.writeAndFlush(firstMessage);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        //ctx.write(msg);
        response.setResult(msg);
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
