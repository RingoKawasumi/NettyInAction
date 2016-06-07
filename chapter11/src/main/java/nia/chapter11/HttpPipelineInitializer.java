package nia.chapter11;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpRequestEncoder;
import io.netty.handler.codec.http.HttpResponseDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;

/**
 * Created by zhujie on 16/6/4.
 */
public class HttpPipelineInitializer extends ChannelInitializer<Channel> {

    private final boolean client;

    public HttpPipelineInitializer(boolean client) {
        this.client = client;
    }

    @Override
    protected void initChannel(Channel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        if (client) {
            // If client, adds HttpResponseDecoder to handle responses from the server
            pipeline.addLast("decoder", new HttpResponseDecoder());
            // If client, adds HttpRequestEncoder to send requests to the server
            pipeline.addLast("encoder", new HttpRequestEncoder());
        } else {
            // If server, adds HttpRequestDecoder to receive requests from the client
            pipeline.addLast("decoder", new HttpRequestDecoder());
            // If server, adds HttpResponseEncoder to send responses to the client
            pipeline.addLast("encoder", new HttpResponseEncoder());
        }
    }
}
