package nia.chapter9;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.TooLongFrameException;

import java.util.List;

/**
 * Created by zhujie on 16/6/8.
 */
public class FrameChunkDecoder extends ByteToMessageDecoder {

    private final int maxFrameSize;

    public FrameChunkDecoder(int maxFrameSize) {
        this.maxFrameSize = maxFrameSize;
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        // Specifies the maximum allowable size of the frames to be produced
        int readableBytes = in.readableBytes();
        if (readableBytes > maxFrameSize) {
            // Discards the frame if it’s too large and throws a TooLongFrameException
            in.clear();
            throw new TooLongFrameException();
        }
        // ...otherwise, reads the new frame from the ByteBuf
        ByteBuf buf = in.readBytes(readableBytes);
        // Adds the frame to the List of decoded messages
        out.add(buf);
    }
}
