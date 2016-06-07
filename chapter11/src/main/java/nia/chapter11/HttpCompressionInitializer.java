package nia.chapter11;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.http.HttpClientCodec;
import io.netty.handler.codec.http.HttpContentCompressor;
import io.netty.handler.codec.http.HttpContentDecompressor;
import io.netty.handler.codec.http.HttpServerCodec;

/**
 * Created by zhujie on 16/6/6.
 */
public class HttpCompressionInitializer extends ChannelInitializer<Channel> {

    private final boolean client;

    public HttpCompressionInitializer(boolean client) {
        this.client = client;
    }

    @Override
    protected void initChannel(Channel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        if (client) {
            pipeline.addLast("codec", new HttpClientCodec());
            // If client, adds HttpContent- Decompressor to handle compressed content from the server
            pipeline.addLast("decompressor", new HttpContentDecompressor());
        } else {
            pipeline.addLast("codec", new HttpServerCodec());
            // If server, adds Http- ContentCompressor to compress the data (if the client supports it)
            pipeline.addLast("compressor", new HttpContentCompressor());
        }
    }
}
