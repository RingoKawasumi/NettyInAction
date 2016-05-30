package nia.chapter5;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.CompositeByteBuf;
import io.netty.buffer.Unpooled;

/**
 * Created by zhujie on 16/5/30.
 */
public class ByteBufExample {

    private static void heapByteBuf() {
        ByteBuf heapBuf = null;
        // Checks whether ByteBuf has a backing array
        if (heapBuf.hasArray()) {
            // if so, gets a reference to the array
            byte[] array = heapBuf.array();
            // Calculates the offset of the first byte
            int offset = heapBuf.arrayOffset() + heapBuf.readerIndex();
            // Gets the number of readable bytes
            int length = heapBuf.readableBytes();
            // Calls your method using array, offset, and length as parameters
            handleArray(array, offset, length);
        }
    }

    private static void directByteBuf() {
        ByteBuf directBuf = null;
        // Checks if ByteBuf isn’t backed by an array. If not, this is a direct buffer.
        if (!directBuf.hasArray()) {
            // Gets the number of readable bytes
            int length = directBuf.readableBytes();
            // Allocates a new array to hold length bytes
            byte[] array = new byte[length];
            // Copies bytes into the array
            directBuf.getBytes(directBuf.readerIndex(), array);
            // Calls some method with array, offset, and length parameters
            handleArray(array, 0, length);
        }
    }

    private static void compositeByteBuf() {
        CompositeByteBuf compBuf = Unpooled.compositeBuffer();
        ByteBuf headerBuf = null;
        ByteBuf bodyBuf = null;
        // Appends ByteBuf instances to the CompositeByteBuf
        compBuf.addComponents(headerBuf, bodyBuf);
        // Removes ByteBuf at index 0 (first component)
        compBuf.removeComponent(0);
        // Loops over all the ByteBuf instances
        for (ByteBuf buf : compBuf) {
            System.out.println(buf.toString());
        }
        // Gets the number of readable bytes
        int length = compBuf.readableBytes();
        // Allocates a new array with length of readable bytes
        byte[] array = new byte[length];
        // Reads bytes into the array
        compBuf.getBytes(compBuf.readerIndex(), array);
        // Uses the array with offset and length parameters
        handleArray(array, 0, array.length);
    }

    private static void handleArray(byte[] array, int offset, int length) {
        //...
    }
}
