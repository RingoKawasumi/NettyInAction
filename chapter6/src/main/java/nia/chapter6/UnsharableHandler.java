package nia.chapter6;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * Created by zhujie on 16/5/31.
 */
// Annotates with @Sharable
@ChannelHandler.Sharable
public class UnsharableHandler extends ChannelInboundHandlerAdapter {

    private int count;

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        // Increments the count field
        count++;
        // Logs method call and forwards to next ChannelHandler
        System.out.println("inboundBufferUpdated(...) called the " + count + " time");
        ctx.fireChannelRead(msg);
    }
}
