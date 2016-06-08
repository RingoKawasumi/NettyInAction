package nia.test.chapter9;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.embedded.EmbeddedChannel;
import nia.chapter9.AbsIntegerEncoder;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by zhujie on 16/6/8.
 */
public class AbsIntegerEncoderTest {
    @Test
    public void testEncoded() {
        ByteBuf buf = Unpooled.buffer();
        // Creates a ByteBuf and writes 9 negative ints
        for (int i = 1; i < 10; i++) {
            buf.writeInt(i * -1);
        }
        // Creates an EmbeddedChannel and installs an AbsIntegerEncoder to be tested
        EmbeddedChannel channel = new EmbeddedChannel(new AbsIntegerEncoder());
        // Writes the ByteBuf and asserts that readOutbound() willproducedata
        assertTrue(channel.writeOutbound(buf));
        // Marks the channel finished
        assertTrue(channel.finish());
        // Reads the produced messages and asserts that they contain absolute values
        for (int i = 1; i < 10; i++) {
            assertEquals(i, channel.readOutbound());
        }
        assertNull(channel.readOutbound());
    }
}
