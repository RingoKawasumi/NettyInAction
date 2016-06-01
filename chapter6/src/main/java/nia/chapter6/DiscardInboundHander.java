package nia.chapter6;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.ReferenceCountUtil;

/**
 * Created by zhujie on 16/5/30.
 */
@ChannelHandler.Sharable
public class DiscardInboundHander extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        // Releases resource by using ReferenceCountUtil.release()
        ReferenceCountUtil.release(msg);
    }
}
