package nia.chapter9;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * Created by zhujie on 16/6/8.
 */
// Extends ByteToMessageDecoder to handle inbound bytes and decode them to messages
public class FixedLengthFrameDecoder extends ByteToMessageDecoder {

    private final int frameLength;

    // Specifies the length of the frames to be produced
    public FixedLengthFrameDecoder(int frameLength) {
        if (frameLength <= 0) {
            throw new IllegalArgumentException(
                    "frameLength must be a positive integer: " + frameLength);
        }
        this.frameLength = frameLength;
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        // Checks if enough bytes can be read to produce the next frame
        while (in.readableBytes() > frameLength) {
            // Reads a new frame out of the ByteBuf
            ByteBuf buf = in.readBytes(frameLength);
            // Adds the frame to the List of decoded messages
            out.add(buf);
        }
    }
}
