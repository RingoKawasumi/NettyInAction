package nia.chapter11;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.handler.timeout.IdleStateHandler;
import io.netty.util.CharsetUtil;

import java.util.concurrent.TimeUnit;

/**
 * Created by zhujie on 16/6/6.
 */
public class IdleStateHandlerInitializer extends ChannelInitializer<Channel> {
    @Override
    protected void initChannel(Channel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        // IdleStateHandler sends an IdleStateEvent when triggered
        pipeline.addLast(new IdleStateHandler(0, 0, 60, TimeUnit.SECONDS));
        // Adds a Heartbeat- Handler to the pipeline
        pipeline.addLast(new HeartbeatHandler());
    }

    // Implements userEvent- Triggered() to send the heartbeat
    public static final class HeartbeatHandler extends ChannelInboundHandlerAdapter {
        // The heartbeat to send to the remote peer
        private static final ByteBuf HEARTBEAT_SEQUENCE = Unpooled.unreleasableBuffer(Unpooled.copiedBuffer("HEARTBEAT", CharsetUtil.ISO_8859_1));

        @Override
        public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
            // Sends the heartbeat and closes the connection if the send fails
            if (evt instanceof IdleStateEvent) {
                ctx.writeAndFlush(HEARTBEAT_SEQUENCE.duplicate()).addListener(ChannelFutureListener.CLOSE_ON_FAILURE);
            } else {
                // Not an IdleStateEvent, so pass it to the next handler
                super.userEventTriggered(ctx, evt);
            }
        }
    }
}
