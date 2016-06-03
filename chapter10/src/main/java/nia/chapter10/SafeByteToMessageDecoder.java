package nia.chapter10;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.TooLongFrameException;

import java.util.List;

/**
 * Created by zhujie on 16/6/2.
 */
// Extends ByteToMessageDecoder to decode bytes to messages
public class SafeByteToMessageDecoder extends ByteToMessageDecoder {
    private static final int MAX_FRAME_SIZE = 1024;

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        int readable = in.readableBytes();
        // Checks if the buffer has more than MAX_FRAME_SIZE bytes
        if (readable > MAX_FRAME_SIZE) {
            in.skipBytes(readable);
            // Skips all readable bytes, throws TooLongFrameException and notifies ChannelHandlers
            throw new TooLongFrameException("Frame too big");
        }
        // do something
    }
}
