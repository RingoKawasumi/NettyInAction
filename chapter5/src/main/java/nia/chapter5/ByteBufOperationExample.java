package nia.chapter5;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import java.nio.charset.Charset;
import java.util.Random;

/**
 * Created by zhujie on 16/5/30.
 */
public class ByteBufOperationExample {
    public static void accessData() {
        ByteBuf buffer = null;
        for (int i = 0; i < buffer.capacity(); i++) {
            byte b = buffer.getByte(i);
            System.out.println((char)b);
        }
    }

    public static void readAllData() {
        ByteBuf buffer = null;
        while (buffer.isReadable()) {
            System.out.println(buffer.readByte());
        }
    }

    public static void writeData() {
        // Fills the writable bytes of a buffer with random integers.
        Random random = new Random();
        ByteBuf buffer = null;
        while (buffer.writableBytes() >= 4) {
            buffer.writeInt(random.nextInt());
        }
    }

    public static void sliceByteBuf() {
        Charset utf8 = Charset.forName("UTF-8");
        // Creates a ByteBuf, which holds bytes for given string
        ByteBuf buf = Unpooled.copiedBuffer("Netty in Action rocks!", utf8);
        // Creates a new slice of the ByteBuf starting at index 0 and ending at index 14
        ByteBuf sliced = buf.slice(0, 14);
        // Prints “Netty in Action rocks!”
        System.out.println(sliced.toString(utf8));
        // Updates the byte at index 0
        buf.setByte(0, (byte) 'J');
        // Succeeds because the data is shared—modifications made to one will be visible in the other
        System.out.println(buf.getByte(0) == sliced.getByte(0));
    }

    public static void copyByteBuf() {
        Charset utf8 = Charset.forName("UTF-8");
        // Creates a ByteBuf to hold the bytes of the supplied String
        ByteBuf buf = Unpooled.copiedBuffer("Netty in Action rocks!", utf8);
        // Creates a copy of a segment of the ByteBuf starting at index 0 and ending at index 14
        ByteBuf copy = buf.copy(0, 14);
        // Prints “Netty in Action rocks!”
        System.out.println(copy.toString(utf8));
        // Updates the byte at index 0
        buf.setByte(0, (byte) 'J');
        // Succeeds because the data isn’t shared
        System.out.println(buf.getByte(0) != copy.getByte(0));
    }

    public static void getAndSet() {
        Charset utf8 = Charset.forName("UTF-8");
        ByteBuf buf = Unpooled.copiedBuffer("Netty in Action rocks!", utf8);
        System.out.println((char)buf.getByte(0));
        // Stores the current readerIndex and writerIndex
        int readerIndex = buf.readerIndex();
        int writerIndex = buf.writerIndex();
        buf.setByte(0, (byte) 'B');
        System.out.println((char) buf.getByte(0));
        // Succeeds because these operations don’t modify the indices
        System.out.println(readerIndex == buf.readerIndex());
        System.out.println(writerIndex == buf.writerIndex());
    }

    public static void readAndWrite() {
        Charset utf8 = Charset.forName("UTF-8");
        ByteBuf buf = Unpooled.copiedBuffer("Netty in Action rocks!", utf8);
        System.out.println((char)buf.readByte());
        // Stores the current readerIndex and writerIndex
        int readerIndex = buf.readerIndex();
        int writerIndex = buf.writerIndex();
        buf.writeByte((byte) '?');
        // Succeeds because writeByte() moved the writerIndex
        System.out.println(readerIndex == buf.readerIndex());
        System.out.println(writerIndex != buf.writerIndex());
    }
}
