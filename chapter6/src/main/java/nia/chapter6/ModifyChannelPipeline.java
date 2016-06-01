package nia.chapter6;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelPipeline;

/**
 * Created by zhujie on 16/5/31.
 */
public class ModifyChannelPipeline {
    public static void modifyPipeline() {
        ChannelPipeline pipeline = null;
        // Creates a FirstHandler instance
        FirstHandler firstHandler = new FirstHandler();
        // Adds this instance to the ChannelPipeline as “handler1”
        pipeline.addLast("handler1", firstHandler);
        // Adds an instance of a SecondHandler to the ChannelPipeline in the first slot,
        // as “handler2”. It will be placed before the existing “handler1”.
        pipeline.addFirst("handler2", new SecondHandler());
        // Adds a ThirdHandler instance to the ChannelPipeline in the last slot as “handler3”.
        pipeline.addLast("handler3", new ThirdHandler());
        // Removes “handler3” by name.
        pipeline.remove("handler3");
        // Removes the FirstHandler by reference (it’s unique, so its name is not needed).
        pipeline.remove(firstHandler);
        // Replaces the SecondHandler (“handler2”) with a FourthHandler: “handler4”.
        pipeline.replace("handler2", "handler4", new FourthHandler());
    }

    private static final class FirstHandler extends ChannelHandlerAdapter {
    }

    private static final class SecondHandler extends ChannelHandlerAdapter {
    }

    private static final class ThirdHandler extends ChannelHandlerAdapter {
    }

    private static final class FourthHandler extends ChannelHandlerAdapter {
    }
}
