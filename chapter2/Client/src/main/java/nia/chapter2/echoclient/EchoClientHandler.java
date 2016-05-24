package nia.chapter2.echoclient;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

/**
 * Created by zhujie on 16/5/24.
 */
// Marks this class as one whose instances can be shared among channels
@ChannelHandler.Sharable
public class EchoClientHandler extends SimpleChannelInboundHandler<ByteBuf> {
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        // When notified that the channel is active, sends a message
        ctx.writeAndFlush(Unpooled.copiedBuffer("Netty rocks!", CharsetUtil.UTF_8));
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ByteBuf in) throws Exception {
        // Logs a dump of the received message
        byte[] b = new byte[in.readableBytes()];
        in.getBytes(0, b, 0, in.readableBytes());
        String s = new String(b);
        System.out.println("Client received: " + s);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        // On exception, logs the error and closes channel
        cause.printStackTrace();
        ctx.close();
    }

}
