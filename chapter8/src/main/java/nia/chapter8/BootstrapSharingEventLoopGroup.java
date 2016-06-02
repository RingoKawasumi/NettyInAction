package nia.chapter8;

import io.netty.bootstrap.Bootstrap;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.net.InetSocketAddress;

/**
 * Created by zhujie on 16/6/1.
 */
public class BootstrapSharingEventLoopGroup {

    public void bootstrap() {
        // Creates a ServerBootstrap to create SocketChannels and bind them
        ServerBootstrap bootstrap = new ServerBootstrap();
        // Sets the EventLoopGroups that provide EventLoops for processing Channel events
        bootstrap.group(new NioEventLoopGroup(), new NioEventLoopGroup())
                // Specifies the Channel implementation to be used
                .channel(NioServerSocketChannel.class)
                // Sets a ChannelInbound- Handler for I/O and data for accepted channels
                .childHandler(new SimpleChannelInboundHandler<ByteBuf>() {
                    ChannelFuture connectFurure;

                    @Override
                    public void channelActive(ChannelHandlerContext ctx) throws Exception {
                        // Creates a Bootstrap to connect to remote host
                        Bootstrap bootstrap = new Bootstrap();
                        // Specifies the channel implementation
                        bootstrap.channel(NioSocketChannel.class)
                                // Sets a handler for inbound I/O
                                .handler(new SimpleChannelInboundHandler<ByteBuf>() {
                                    @Override
                                    protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
                                        System.out.println("Received data");
                                    }
                                });
                        // Uses the same EventLoop as the one assigned to the accepted channel
                        bootstrap.group(ctx.channel().eventLoop());
                        // Connects to remote peer
                        connectFurure = bootstrap.connect(new InetSocketAddress("www.manning.com", 80));
                    }

                    @Override
                    protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
                        // When the connection is complete performs some data operation (such as proxying)
                        if (connectFurure.isDone()) {
                            // do something with the data
                        }
                    }
                });
        // Binds the channel via configured Bootstrap
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
