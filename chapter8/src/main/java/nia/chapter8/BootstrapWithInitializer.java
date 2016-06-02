package nia.chapter8;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpClientCodec;
import io.netty.handler.codec.http.HttpObjectAggregator;

import java.net.InetSocketAddress;

/**
 * Created by zhujie on 16/6/1.
 */
public class BootstrapWithInitializer {

    public void bootstrap() {
        // Creates a ServerBootstrap to create and bind new Channels
        ServerBootstrap bootstrap = new ServerBootstrap();
        // Sets the EventLoopGroup that provides EventLoops for processing Channel events
        bootstrap.group(new NioEventLoopGroup(), new NioEventLoopGroup())
                // Specifies the Channel implementation
                .channel(NioServerSocketChannel.class)
                // Registers an instance of ChannelInitializerImpl to set up the ChannelPipeline
                .childHandler(new ChannelInitializerImpl());
        // Binds to an address
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

    // Custom implementation of ChannelInitializerImpl to set up the ChannelPipeline
    public final class ChannelInitializerImpl extends ChannelInitializer<Channel> {

        @Override
        protected void initChannel(Channel ch) throws Exception {
            // Adds the required handlers to the ChannelPipeline
            ChannelPipeline pipeline = ch.pipeline();
            pipeline.addLast(new HttpClientCodec());
            pipeline.addLast(new HttpObjectAggregator(Integer.MAX_VALUE));
        }
    }
}
