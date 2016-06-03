package nia.chapter10;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;

import java.util.List;

/**
 * Created by zhujie on 16/6/2.
 */
// Extends MessageToMessage- Decoder<Integer>
public class IntegerToStringDecoder extends MessageToMessageDecoder<Integer>{
    @Override
    protected void decode(ChannelHandlerContext ctx, Integer msg, List<Object> out) throws Exception {
        // Converts the Integer message to its String representation and adds it to the output List
        out.add(String.valueOf(msg));
    }
}
