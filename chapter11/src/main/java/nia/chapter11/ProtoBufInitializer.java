package nia.chapter11;

import com.google.protobuf.MessageLite;
import io.netty.channel.*;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;

/**
 * Created by zhujie on 16/6/7.
 */
public class ProtoBufInitializer extends ChannelInitializer<Channel> {

    private final MessageLite lite;

    public ProtoBufInitializer(MessageLite lite) {
        this.lite = lite;
    }

    @Override
    protected void initChannel(Channel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        // Adds Protobuf- Varint32Frame- Decoder to break down frames
        pipeline.addLast(new ProtobufVarint32FrameDecoder());
        // Adds Protobuf- Encoder to handle encoding of messages
        pipeline.addLast(new ProtobufEncoder());
        // Adds ProtobufDecoder to decode messages
        pipeline.addLast(new ProtobufDecoder(lite));
        // Adds ObjectHandler to handle the decoded messages
        pipeline.addLast(new Objecthandler());
    }

    public static final class Objecthandler extends SimpleChannelInboundHandler<Object> {

        @Override
        protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
            // Do something with the object
        }
    }
}
