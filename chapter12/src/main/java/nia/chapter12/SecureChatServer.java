package nia.chapter12;

import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.group.ChannelGroup;
import nia.util.BogusSslContextFactory;

import javax.net.ssl.SSLContext;
import java.net.InetSocketAddress;

/**
 * Created by zhujie on 16/6/8.
 */
// SecureChatServer extends ChatServer to support encryption
public class SecureChatServer extends ChatServer {

    private final SSLContext context;

    public SecureChatServer(SSLContext context) {
        this.context = context;
    }

    public static void main(String[] args) {
        if (args.length != 1) {
            System.err.println("Please give port as argument");
            System.exit(1);
        }
        int port = Integer.parseInt(args[0]);
        System.out.println("Starting SecureChatServer on port " + port);
        SSLContext context = BogusSslContextFactory.getServerContext();
        final SecureChatServer endpoint = new SecureChatServer(context);
        ChannelFuture future = endpoint.start(new InetSocketAddress(port));
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                endpoint.destroy();
            }
        });
        future.channel().closeFuture().syncUninterruptibly();
    }

    @Override
    protected ChannelInitializer<Channel> createInitializer(ChannelGroup group) {
        // Returns the previously created SecureChat- ServerInitializer to enable encryption
        return new SecureChatServerInitializer(group, context);
    }
}
