package nia.chapter11;

import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.handler.codec.LineBasedFrameDecoder;

/**
 * Created by zhujie on 16/6/6.
 */
public class LineBaseHandlerInitializer extends ChannelInitializer<Channel> {
    @Override
    protected void initChannel(Channel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        // The LineBased- FrameDecoder forwards extracted frames to the next handler
        pipeline.addLast(new LineBasedFrameDecoder(65 * 1024));
        // Adds the FrameHandler to receive the frames
        pipeline.addLast(new FrameHandler());
    }

    public static final class FrameHandler extends SimpleChannelInboundHandler<ByteBuf> {

        @Override
        // Passes in the contents of a single frame
        protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
            // Do something with the frame
        }
    }
}
