package com.zhang.rpc.client;

import com.zhang.rpc.RPCRequest;
import com.zhang.rpc.RPCResponse;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;

/**
 * Created by zhangc on 2018/5/28.
 */
public class RPCClient {
    private static Object obj = new Object();

    public <T> T send(RPCRequest rb) throws InterruptedException {
        // Configure the client.
        final RPCResponse response = new RPCResponse();

        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(group)
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.TCP_NODELAY, true)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        public void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline p = ch.pipeline();

                            p.addLast(
                                    new ObjectEncoder(),
                                    new ObjectDecoder(ClassResolvers.cacheDisabled(null)),
                                    new ClientHandler(response,obj));
                        }
                    });

            // Start the client.
            ChannelFuture future = b.connect("127.0.0.1", 8888).sync();

            //ChannelFuture future = bootstrap.connect(host, port).sync();
            //将request对象写入outbundle处理后发出（即RpcEncoder编码器）
            future.channel().writeAndFlush(rb).sync();

            // 用线程等待的方式决定是否关闭连接
            // 其意义是：先在此阻塞，等待获取到服务端的返回后，被唤醒，从而关闭网络连接

            synchronized (obj) {
                obj.wait();
            }
            if (response != null && response.getResult() != null) {
                future.channel().closeFuture().sync();
            }
            return (T) response.getResult();
        } finally {
            // Shut down the event loop to terminate all threads.
            group.shutdownGracefully();
        }
    }
}
