package nia.chapter13;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.socket.DatagramPacket;
import io.netty.handler.codec.MessageToMessageEncoder;
import io.netty.util.CharsetUtil;

import java.net.InetSocketAddress;
import java.util.List;

/**
 * Created by zhujie on 16/6/12.
 */
public class LogEventEncoder extends MessageToMessageEncoder<LogEvent> {

    private final InetSocketAddress remoteAddress;

    // LogEventEncoder creates Datagram- Packet messages to be sent to the specified InetSocketAddress
    public LogEventEncoder(InetSocketAddress remoteAddress) {
        this.remoteAddress = remoteAddress;
    }

    @Override
    protected void encode(ChannelHandlerContext ctx, LogEvent logEvent, List<Object> out) throws Exception {
        ByteBuf buf = ctx.alloc().buffer();
        buf.writeBytes(logEvent.getLogfile().getBytes(CharsetUtil.UTF_8));
        buf.writeByte(logEvent.SEPARATOR);
        // Writes the log message to the ByteBuf
        buf.writeBytes(logEvent.getMsg().getBytes(CharsetUtil.UTF_8));
        // Adds a new DatagramPacket with the data and destination address to the list of outbound messages
        out.add(new DatagramPacket(buf, remoteAddress));
    }
}
