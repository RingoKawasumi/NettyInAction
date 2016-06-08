package nia.chapter12;

import io.netty.channel.Channel;
import io.netty.channel.group.ChannelGroup;
import io.netty.handler.ssl.SslHandler;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLEngine;

/**
 * Created by zhujie on 16/6/8.
 */
// Extends ChatServerInitializer to add encryption
public class SecureChatServerInitializer extends ChatServerInitializer {

    private final SSLContext context;

    public SecureChatServerInitializer(ChannelGroup group, SSLContext context) {
        super(group);
        this.context = context;
    }

    @Override
    protected void initChannel(Channel ch) throws Exception {
        // Calls the parent’s initChannel()
        super.initChannel(ch);
        SSLEngine engine = context.createSSLEngine();
        engine.setUseClientMode(false);
        // Adds the SslHandler to the ChannelPipeline
        ch.pipeline().addFirst(new SslHandler(engine));
    }
}
