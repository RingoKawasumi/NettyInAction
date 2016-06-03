package nia.chapter10;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;

import java.util.List;

/**
 * Created by zhujie on 16/6/3.
 */
// Extends MessageToMessageEncoder
public class IntegerToStringEncoder extends MessageToMessageEncoder<Integer> {
    @Override
    protected void encode(ChannelHandlerContext ctx, Integer msg, List<Object> out) throws Exception {
        // Converts the Integer to a String and adds it to the List
        out.add(String.valueOf(msg));
    }
}
