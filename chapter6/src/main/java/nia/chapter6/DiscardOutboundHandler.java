package nia.chapter6;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;
import io.netty.util.ReferenceCountUtil;

/**
 * Created by zhujie on 16/5/30.
 */
@ChannelHandler.Sharable
public class DiscardOutboundHandler extends ChannelOutboundHandlerAdapter {
    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
        // Releases resource by using ReferenceCountUtil.release
        ReferenceCountUtil.release(msg);
        // Notifies ChannelPromise that data was handled
        promise.setSuccess();
    }
}
