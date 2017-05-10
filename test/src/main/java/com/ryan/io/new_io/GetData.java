package com.ryan.io.new_io;

import com.ryan.util.Util;

import java.nio.ByteBuffer;

/**
 * Created by MUFCRyan on 2017/5/10.
 * Book Page556 Chapter 18.10.2 获取基本类型
 */

public class GetData {
    private static final int SIZE = 1024;
    public static void main(String[] args){
        ByteBuffer buffer = ByteBuffer.allocate(SIZE);
        int i = 0;
        while (i++ < buffer.limit()){
            if (buffer.get() != 0)
                Util.println("nonzero");
        }
        Util.println(i);
        buffer.rewind();
        // Store and read a char array
        buffer.asCharBuffer().put("MUFCRyan");
        char c;
        while ((c = buffer.getChar()) != 0){
            Util.print(c + " ");
        }
        Util.println();
        buffer.rewind();
        // Store and read a short
        buffer.asShortBuffer().put((short) 471142);
        Util.println(buffer.getShort());
        buffer.rewind();
        // Store and read an int
        buffer.asIntBuffer().put(77777);
        Util.println(buffer.getInt());
        buffer.rewind();
        // Store and read a long
        buffer.asLongBuffer().put(999999999);
        Util.println(buffer.getLong());
        buffer.rewind();
        // Store and read a float
        buffer.asFloatBuffer().put(888888888);
        Util.println(buffer.getFloat());
        buffer.rewind();
        // Store and read a double
        buffer.asDoubleBuffer().put(555555555);
        Util.println(buffer.getDouble());
        buffer.rewind();
    }
}
