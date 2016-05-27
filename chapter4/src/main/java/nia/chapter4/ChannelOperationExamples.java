package nia.chapter4;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.util.CharsetUtil;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Created by zhujie on 16/5/27.
 */
public class ChannelOperationExamples {
    public static void writingToChannel() {
        Channel channel = null;// Get the channel reference from somewhere
        // Creates ByteBuf that holds the data to write
        ByteBuf buf = Unpooled.copiedBuffer("your data", CharsetUtil.UTF_8);
        // Writes the data and flushes it
        ChannelFuture cf = channel.write(buf);
        // Adds ChannelFuture- Listener to receive notification after write completes
        cf.addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture future) throws Exception {
                if (future.isSuccess()) {
                    // Write operation completes without error
                    System.out.println("Write successful");
                } else {
                    // Logs an error
                    System.out.println("Write error");
                    future.cause().printStackTrace();
                }
            }
        });
    }

    public static void writingToChannelManyThreads() {
        final Channel channel = null; // Get the channel reference from somewhere
        final ByteBuf buf = Unpooled.copiedBuffer("your data", CharsetUtil.UTF_8);
        Runnable writer = new Runnable() {
            @Override
            public void run() {
                channel.write(buf.duplicate());
            }
        };
        Executor executor = Executors.newCachedThreadPool();

        // write in one thread
        executor.execute(writer);

        // write in another thread
        executor.execute(writer);
    }

}
