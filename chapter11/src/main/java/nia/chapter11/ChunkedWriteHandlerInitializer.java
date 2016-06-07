package nia.chapter11;

import io.netty.channel.*;
import io.netty.handler.stream.ChunkedStream;
import io.netty.handler.stream.ChunkedWriteHandler;

import java.io.File;
import java.io.FileInputStream;

/**
 * Created by zhujie on 16/6/6.
 */
public class ChunkedWriteHandlerInitializer extends ChannelInitializer<Channel> {

    private final File file;

    public ChunkedWriteHandlerInitializer(File file) {
        this.file = file;
    }

    @Override
    protected void initChannel(Channel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        // Adds a Chunked- WriteHandler to handle data passed in as ChunkedInput
        pipeline.addLast(new ChunkedWriteHandler());
        // Adds a Chunked- WriteHandler to handle data passed in as ChunkedInput
        pipeline.addLast(new WriteStreamHandler());
    }

    public final class WriteStreamHandler extends ChannelInboundHandlerAdapter {
        @Override
        // channelActive() writes the file data using Chunked- Input when the connection is established.
        public void channelActive(ChannelHandlerContext ctx) throws Exception {
            super.channelActive(ctx);
            ctx.writeAndFlush(new ChunkedStream(new FileInputStream(file)));
        }
    }
}
