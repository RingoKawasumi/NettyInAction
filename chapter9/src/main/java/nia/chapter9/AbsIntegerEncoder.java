package nia.chapter9;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;

import java.util.List;

/**
 * Created by zhujie on 16/6/8.
 */
public class AbsIntegerEncoder extends MessageToMessageEncoder<ByteBuf>{
    @Override
    protected void encode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        // Checks if there are enough bytes to encode
        while (in.readableBytes() >= 4) {
            // Reads the next int out of the input ByteBuf and calculates the absolute value
            int value = Math.abs(in.readInt());
            // Writes the int to the List of encoded messages
            out.add(value);
        }
    }
}
