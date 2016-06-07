package nia.chapter11;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.handler.ssl.SslHandler;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLEngine;

/**
 * Created by zhujie on 16/6/3.
 */
public class SslChannelInitializer extends ChannelInitializer<Channel> {

    private final SSLContext context;

    private final boolean client;

    private final boolean startTls;

    // Passes in the SslContext to use
    public SslChannelInitializer(SSLContext context, boolean client, boolean startTls) {
        // If true, the first message written is not encrypted (clients should set to true)
        this.context = context;
        this.client = client;
        this.startTls = startTls;
    }

    @Override
    protected void initChannel(Channel ch) throws Exception {
        // For each SslHandler instance, obtains a new SSLEngine from the SslContext using the ByteBufAllocator of the Channel
        SSLEngine engine = context.createSSLEngine();
        engine.setUseClientMode(client);
        // Adds the SslHandler to the pipeline as the first handler
        ch.pipeline().addFirst("ssl", new SslHandler(engine, startTls));
    }
}
