package nia.chapter8;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.net.InetSocketAddress;

/**
 * Created by zhujie on 16/6/1.
 */
public class BootstrapClient {

    public static void main(String[] args) {
        BootstrapClient client = new BootstrapClient();
        client.bootstrap();
    }

    public void bootstrap() {
        EventLoopGroup group = new NioEventLoopGroup();
        // Creates a Bootstrap to create and connect new client channels
        Bootstrap bootstrap = new Bootstrap();
        // Sets the EventLoopGroup that provides EventLoops for processing Channel events
        bootstrap.group(group)
                // Specifies the Channel implementation to be used
                .channel(NioSocketChannel.class)
                // Sets the handler for Channel events and data
                .handler(new SimpleChannelInboundHandler<ByteBuf>() {
                    @Override
                    protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
                        System.out.println("Received data");
                        msg.clear();
                    }
                });
        // Connects to the remote host
        ChannelFuture future = bootstrap.connect(new InetSocketAddress("www.manning.com", 80));
        future.addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture future) throws Exception {
                if (future.isSuccess()) {
                    System.out.println("Connection established");
                } else {
                    System.err.println("Connection attempt failed");
                    future.cause().printStackTrace();
                }
            }
        });
    }
}
