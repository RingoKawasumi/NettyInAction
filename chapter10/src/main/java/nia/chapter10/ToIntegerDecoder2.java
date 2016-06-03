package nia.chapter10;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ReplayingDecoder;

import java.util.List;

/**
 * Created by zhujie on 16/6/2.
 */
// Extends Replaying- Decoder<Void> to decode bytes to messages
public class ToIntegerDecoder2 extends ReplayingDecoder<Void> {
    @Override
    // The incoming ByteBuf is a Replaying- DecoderBuffer.
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        // Reads an int from the inbound ByteBuf and adds it to the List of decoded messages
        out.add(in.readInt());
    }
}
