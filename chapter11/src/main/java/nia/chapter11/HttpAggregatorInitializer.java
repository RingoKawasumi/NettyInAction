package nia.chapter11;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.http.HttpClientCodec;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;

/**
 * Created by zhujie on 16/6/6.
 */
public class HttpAggregatorInitializer extends ChannelInitializer<Channel>{

    private final boolean client;

    public HttpAggregatorInitializer(boolean client) {
        this.client = client;
    }

    @Override
    protected void initChannel(Channel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        if (client) {
            // If client, adds HttpClientCodec
            pipeline.addLast("codec", new HttpClientCodec());
        } else {
            // If server, adds HttpServerCodec
            pipeline.addLast("codec", new HttpServerCodec());
        }
        // Adds HttpObjectAggregator with a max message size of 512 KB to the pipeline
        pipeline.addLast("aggegator", new HttpObjectAggregator(512 * 1024));
    }
}
