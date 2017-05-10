package com.ryan.io.new_io;

import com.ryan.util.Util;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;

/**
 * Created by MUFCRyan on 2017/5/10.
 * Book Page557 Chapter 18.10.3 视图缓冲器（view buffer）：可通过某个特定的基本数据类型的视窗查看其底层的
 * ByteBuffer，ByteBuffer 仍旧是存储数据的地方，支持着前边的视图 --> 故对视图的任何修改都会映射到 ByteBuffer 上导致 ByteBuffer 也被修改
 */

public class IntBufferDemo {
    private static final int SIZE = 1024;
    public static void main(String[] args){
        ByteBuffer buffer = ByteBuffer.allocate(SIZE);
        IntBuffer intBuffer = buffer.asIntBuffer();
        // Store an array of int
        intBuffer.put(new int[]{11, 22, 33, 44, 55, 66, 77, 88, 99});
        // Absolute location read and write
        Util.println(intBuffer.get(3));
        intBuffer.put(3, 313);
        // Setting a new limit before rewinding the buffer
        intBuffer.flip();
        while (intBuffer.hasRemaining()){
            int i = intBuffer.get();
            Util.println(i);
        }
    }
}
