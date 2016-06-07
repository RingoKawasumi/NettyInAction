package nia.chapter11;

import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

/**
 * Created by zhujie on 16/6/6.
 */
public class LengthBasedInitializer extends ChannelInitializer<Channel> {
    @Override
    protected void initChannel(Channel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        // LengthFieldBasedFrameDecoder for messages that encode frame length in the first 8 bytes
        pipeline.addLast(new LengthFieldBasedFrameDecoder(65 * 1024, 0, 8));
        // Adds a FrameHandler to handle each frame
        pipeline.addLast(new FrameHandler());
    }

    public static final class FrameHandler extends SimpleChannelInboundHandler<ByteBuf> {

        @Override
        protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
            // Processes the frame data
            // Do something with the frame
        }
    }
}
