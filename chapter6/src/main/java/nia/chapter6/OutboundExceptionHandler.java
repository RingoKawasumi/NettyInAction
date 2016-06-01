package nia.chapter6;

import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;

/**
 * Created by zhujie on 16/6/1.
 */
public class OutboundExceptionHandler {
    public static void exceptionHandler(Channel channel, String msg) {
        ChannelFuture future = channel.write(msg);
        future.addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture future) throws Exception {
                if (!future.isSuccess()) {
                    future.cause().printStackTrace();
                    future.channel().close();
                }
            }
        });
    }
}
