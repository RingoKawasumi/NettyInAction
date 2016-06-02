package nia.chapter8;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.oio.OioEventLoopGroup;
import io.netty.channel.socket.DatagramPacket;
import io.netty.channel.socket.oio.OioDatagramChannel;

import java.net.InetSocketAddress;

/**
 * Created by zhujie on 16/6/2.
 */
public class BootstrapDatagramChannel {
    public void bootstrap() {
        // Creates a Bootstrap to create and bind new datagram channels
        Bootstrap bootstrap = new Bootstrap();
        // Sets the EventLoopGroup that provides EventLoops for processing Channel events
        bootstrap.group(new OioEventLoopGroup())
                // Specifies the Channel implementation
                .channel(OioDatagramChannel.class)
                // Sets a Channel- InboundHandler to handle I/O and data for the channel
                .handler(new SimpleChannelInboundHandler<DatagramPacket>() {
                    @Override
                    protected void channelRead0(ChannelHandlerContext ctx, DatagramPacket msg) throws Exception {
                        // Do something with the packet
                    }
                });
        // Calls bind() because the protocol is connectionless
        ChannelFuture future = bootstrap.bind(new InetSocketAddress(0));
        future.addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture future) throws Exception {
                if (future.isSuccess()) {
                    System.out.println("Channel bound");
                } else {
                    System.err.println("Bind attempt failed");
                    future.cause().printStackTrace();
                }
            }
        });
    }
}
