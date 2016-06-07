package nia.chapter11;

import io.netty.channel.*;
import io.netty.handler.codec.marshalling.MarshallerProvider;
import io.netty.handler.codec.marshalling.MarshallingDecoder;
import io.netty.handler.codec.marshalling.MarshallingEncoder;
import io.netty.handler.codec.marshalling.UnmarshallerProvider;

import java.io.Serializable;

/**
 * Created by zhujie on 16/6/7.
 */
public class MarshallingInitializer extends ChannelInitializer<Channel> {

    private final MarshallerProvider marshallerProvider;
    private final UnmarshallerProvider unmarshallerProvider;

    public MarshallingInitializer(MarshallerProvider marshallerProvider, UnmarshallerProvider unmarshallerProvider) {
        this.marshallerProvider = marshallerProvider;
        this.unmarshallerProvider = unmarshallerProvider;
    }

    @Override
    protected void initChannel(Channel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        // Adds a Marshalling- Decoder to convert ByteBufs to POJOs
        pipeline.addLast(new MarshallingDecoder(unmarshallerProvider));
        // Adds a Marshalling- Encoder to convert POJOs to ByteBufs
        pipeline.addLast(new MarshallingEncoder(marshallerProvider));
        // Adds an ObjectHandler for normal POJOs that implement Serializable
        pipeline.addLast(new ObjectHandler());
    }

    public static final class ObjectHandler extends SimpleChannelInboundHandler<Serializable> {

        @Override
        protected void channelRead0(ChannelHandlerContext ctx, Serializable msg) throws Exception {
            // Do something
        }
    }

}
