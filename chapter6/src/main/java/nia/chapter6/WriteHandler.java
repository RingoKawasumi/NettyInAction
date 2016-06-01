package nia.chapter6;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

/**
 * Created by zhujie on 16/5/31.
 */
public class WriteHandler extends ChannelHandlerAdapter {

    private ChannelHandlerContext ctx;

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        // Stores reference to ChannelHandler- Context for later use
        this.ctx = ctx;
    }

    public void send(String msg) {
        // Sends message using previously stored ChannelHandlerContext
        ctx.writeAndFlush(msg);
    }
}
