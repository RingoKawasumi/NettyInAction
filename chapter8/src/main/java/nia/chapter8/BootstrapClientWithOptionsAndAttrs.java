package nia.chapter8;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOption;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.AttributeKey;

import java.net.InetSocketAddress;

/**
 * Created by zhujie on 16/6/1.
 */
public class BootstrapClientWithOptionsAndAttrs {
    public void bootstrap() {
        // Creates an AttributeKey to identify the attribute
        final AttributeKey<Integer> id = new AttributeKey<>("ID");
        // Creates a Bootstrap to create client channels and connect them
        Bootstrap bootstrap = new Bootstrap();
        // Sets the EventLoopGroup that provides EventLoops for processing Channel events
        bootstrap.group(new NioEventLoopGroup())
                // Specifies the Channel implementation
                .channel(NioSocketChannel.class)
                // Sets a ChannelInboundHandler to handle I/O and data for the channel
                .handler(new SimpleChannelInboundHandler<ByteBuf>() {

                    @Override
                    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
                        // Retrieves the attribute with the AttributeKey and its value
                        Integer idValue = ctx.channel().attr(id).get();
                        // do something with the idValue
                    }

                    @Override
                    protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
                        System.out.println("Received data");
                    }
                });
        // Sets the ChannelOptions that will be set on the created channels on connect() or bind()
        bootstrap.option(ChannelOption.SO_KEEPALIVE, true)
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000);
        // Stores the id attribute
        bootstrap.attr(id, 123456);
        // Connects to the remote host with the configured Bootstrap
        ChannelFuture future = bootstrap.connect(new InetSocketAddress("www.manning.com", 80));
        future.syncUninterruptibly();
    }
}
