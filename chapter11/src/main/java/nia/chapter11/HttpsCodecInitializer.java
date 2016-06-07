package nia.chapter11;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.http.HttpClientCodec;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.ssl.SslHandler;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLEngine;

/**
 * Created by zhujie on 16/6/6.
 */
public class HttpsCodecInitializer extends ChannelInitializer<Channel> {

    private final SSLContext context;

    private final boolean client;

    public HttpsCodecInitializer(SSLContext context, boolean client) {
        this.context = context;
        this.client = client;
    }

    @Override
    protected void initChannel(Channel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        SSLEngine engine = context.createSSLEngine();
        engine.setUseClientMode(client);
        // Adds SslHandler to the pipeline to use HTTPS
        pipeline.addFirst("ssl", new SslHandler(engine));
        if (client) {
            // If client, adds HttpClient- Codec
            pipeline.addLast("codec", new HttpClientCodec());
        } else {
            // If server, adds HttpServerCodec
            pipeline.addLast("codec", new HttpServerCodec());
        }
    }
}
