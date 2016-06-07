package nia.chapter11;

import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.handler.codec.LineBasedFrameDecoder;

/**
 * Created by zhujie on 16/6/6.
 */
public class CmdHandlerInitializer extends ChannelInitializer<Channel> {
    @Override
    protected void initChannel(Channel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        // Adds a CmdDecoder to extract a Cmd object and forwards it to the next handler
        pipeline.addLast(new CmdDecoder(65 * 1024));
        // Adds a CmdHandler to receive and process the Cmd objects
        pipeline.addLast(new CmdHandler());
    }

    // The Cmd POJO
    public static final class Cmd {
        private final ByteBuf name;

        private final ByteBuf args;

        public Cmd(ByteBuf name, ByteBuf args) {
            this.name = name;
            this.args = args;
        }

        public ByteBuf getName() {
            return name;
        }

        public ByteBuf getArgs() {
            return args;
        }
    }

    public static final class CmdDecoder extends LineBasedFrameDecoder {

        public CmdDecoder(int maxLength) {
            super(maxLength);
        }

        @Override
        protected Object decode(ChannelHandlerContext ctx, ByteBuf buffer) throws Exception {
            // Extracts a frame delimited by an end-of-line sequence from the ByteBuf
            ByteBuf frame = (ByteBuf) super.decode(ctx, buffer);
            if (frame == null) {
                // Null is returned if there is no frame in the input.
                return null;
            }
            // Finds the index of the first space character. The command name precedes it, the arguments follow.
            int index = frame.indexOf(frame.readerIndex(), frame.writerIndex(), (byte)' ');
            // New Cmd object instantiated with slices that hold the command name and arguments
            return new Cmd(frame.slice(frame.readerIndex(), index), frame.slice(index + 1, frame.writerIndex()));
        }
    }

    public static final class CmdHandler extends SimpleChannelInboundHandler<Cmd> {

        @Override
        protected void channelRead0(ChannelHandlerContext ctx, Cmd msg) throws Exception {
            // Processes the Cmd object passed through the pipeline
            // Do something with the commmand
        }
    }
}
