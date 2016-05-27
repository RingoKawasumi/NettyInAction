package nia.chapter4;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.oio.OioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.oio.OioServerSocketChannel;

import java.net.InetSocketAddress;
import java.nio.charset.Charset;

/**
 * Created by zhujie on 16/5/26.
 */
public class NettyNioServer {
    public void server(int port) throws Exception {
        final ByteBuf buf = Unpooled.unreleasableBuffer(Unpooled.copiedBuffer("Hi!\r\n", Charset.forName("UTF-8")));
        // Uses NioEvent- LoopGroup for non-blocking mode
        EventLoopGroup group = new NioEventLoopGroup();

        try {
            // ServerBootstrap b = new ServerBootstrap();
            ServerBootstrap b = new ServerBootstrap();
            b.group(group)
                    .channel(NioServerSocketChannel.class)
                    .localAddress(new InetSocketAddress(port))
                    // Specifies ChannelInitializer that will be called for each accepted connection
                    .childHandler(new ChannelInitializer<SocketChannel>() {

                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            // Adds a Channel- InboundHandler- Adapter to intercept and handle events
                            ch.pipeline().addLast(new ChannelInboundHandlerAdapter() {

                                @Override
                                public void channelActive(ChannelHandlerContext ctx) throws Exception {
                                    ctx.write(buf.duplicate()).addListener(ChannelFutureListener.CLOSE);
                                }
                    });
                }
            });
            // Binds server to accept connections
            ChannelFuture f = b.bind().sync();
            f.channel().closeFuture().sync();
        } finally {
            // Releases all resources
            group.shutdownGracefully().sync();
        }
    }
}
