package com.zhang.rpc.client;

import com.zhang.rpc.RPCRequest;
import com.zhang.rpc.RPCResponse;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;
import objectecho.ObjectEchoClientHandler;

/**
 * Created by zhangc on 2018/5/28.
 */
public class RPCClient2 {
    private static Object obj = new Object();
    private RPCResponse response = null;

    public <T> T send(RPCRequest rb) throws InterruptedException {
       /* final SslContext sslCtx;
        if (SSL) {
            sslCtx = SslContextBuilder.forClient()
                    .trustManager(InsecureTrustManagerFactory.INSTANCE).build();
        } else {
            sslCtx = null;
        }*/

        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(group)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        public void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline p = ch.pipeline();
                            /*if (sslCtx != null) {
                                p.addLast(sslCtx.newHandler(ch.alloc(), HOST, PORT));
                            }*/
                            p.addLast(
                                    new ObjectEncoder(),
                                    new ObjectDecoder(ClassResolvers.cacheDisabled(null)),
                                   // new ObjectEchoClientHandler());
                                    new ClientHandler(response));
                        }
                    });

            // Start the connection attempt.
            ChannelFuture future = b.connect("127.0.0.1",8888).sync();
                    //.channel().closeFuture().sync();
            future.channel().writeAndFlush(rb).sync();
            synchronized (obj) {
                obj.wait();
            }
            if (response != null) {
                future.channel().closeFuture().sync();
            }
            return (T) response.getResult();
        } finally {
            group.shutdownGracefully();
        }
    }
}
