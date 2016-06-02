package nia.chapter8;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.net.InetSocketAddress;

/**
 * Created by zhujie on 16/6/1.
 */
public class BootstrapServer {

    public void bootstrap() {
        NioEventLoopGroup group = new NioEventLoopGroup();
        // Creates a Server- Bootstrap
        ServerBootstrap bootstrap = new ServerBootstrap();
        // Sets the EventLoopGroup that provides EventLoops for processing Channel events
        bootstrap.group(group)
                // Specifies the Channel implementation to be used
                .channel(NioServerSocketChannel.class)
                // Sets a ChannelInboundHandler for I/O and data for the accepted channels
                .childHandler(new SimpleChannelInboundHandler<ByteBuf>() {
                    @Override
                    protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
                        System.out.println("Received data");
                        msg.clear();
                    }
                });
        // Binds the channel with the configured bootstrap
        ChannelFuture future = bootstrap.bind(new InetSocketAddress(8080));
        future.addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture future) throws Exception {
                if (future.isSuccess()) {
                    System.out.println("Server bound");
                } else {
                    System.err.println("Bind attempt failed");
                    future.cause().printStackTrace();
                }
            }
        });
    }
}
