package nia.test.chapter9;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.embedded.EmbeddedChannel;
import io.netty.handler.codec.TooLongFrameException;
import nia.chapter9.FrameChunkDecoder;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by zhujie on 16/6/8.
 */
public class FrameChunkDecoderTest {

    @Test
    public void testFramesDecoded() {
        // Creates a ByteBuf and writes 9 bytes to it
        ByteBuf buf = Unpooled.buffer();
        for (int i = 0; i < 9; i++) {
            buf.writeByte(i);
        }
        ByteBuf input = buf.duplicate();
        // Creates an Embedded- Channel and installs a FixedLengthFrame- Decoder with a frame size of 3
        EmbeddedChannel channel = new EmbeddedChannel(new FrameChunkDecoder(3));
        // Writes 2 bytes to it and asserts that they produced a new frame
        assertTrue(channel.writeInbound(input.readBytes(2)));
        try {
            // Writes a 4-byte frame and catches the expected TooLong- FrameException
            channel.writeInbound(input.readBytes(4));
            fail();
        } catch (TooLongFrameException e) {
            // excepted exception
        }
        // Writes the remaining 2 bytes and asserts a valid frame
        assertTrue(channel.writeInbound(input.readBytes(3)));
        // Marks the channel finished
        assertTrue(channel.finish());

        // Reads the produced messages and verifies the values
        ByteBuf read = (ByteBuf) channel.readInbound();
        assertEquals(buf.readSlice(2), read);
        read.release();
        read = (ByteBuf) channel.readInbound();
        assertEquals(buf.skipBytes(4).readSlice(3), read);
        read.release();
        buf.release();
    }
}
