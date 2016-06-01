package nia.chapter6;

import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPipeline;
import io.netty.util.CharsetUtil;

/**
 * Created by zhujie on 16/5/31.
 */
public class EventsViaChannelOutboundInvoker {

    public static void channelFromChannelhandlerContext(ChannelHandlerContext context) {
        ChannelHandlerContext ctx = context;
        // Gets a reference to the Channel associated with the ChannelHandlerContext
        Channel channel = ctx.channel();
        // Writes buffer via the Channel
        channel.write(Unpooled.copiedBuffer("Netty in Action", CharsetUtil.UTF_8));
    }

    public static void channelPipelineFromChannelhandlerContext(ChannelHandlerContext context) {
        ChannelHandlerContext ctx = context;
        // Gets a reference to the ChannelPipeline associated with the ChannelHandlerContext
        ChannelPipeline pipeline = ctx.pipeline();
        // Writes the buffer via the ChannelPipeline
        pipeline.write(Unpooled.copiedBuffer("Netty in Action", CharsetUtil.UTF_8));
    }

    public static void channelHandlerContextWrite(ChannelHandlerContext context) {
        // Gets a reference to a ChannelHandlerContext
        ChannelHandlerContext ctx = context;
        // write() sends the buffer to the next ChannelHandler
        ctx.write(Unpooled.copiedBuffer("Netty in Action", CharsetUtil.UTF_8));
    }

}
