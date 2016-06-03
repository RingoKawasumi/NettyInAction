package nia.chapter10;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * Created by zhujie on 16/6/2.
 */
// Extends ByteTo- MessageDecoder to decode bytes to a specific format
public class ToIntegerDecoder extends ByteToMessageDecoder {
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        // Checks if there are at least 4 bytes readable (length of an int)
        if (in.readableBytes() >= 4) {
            // Reads an int from the inbound ByteBuf and adds it to the List of decoded messages
            out.add(in.readInt());
        }
    }
}
