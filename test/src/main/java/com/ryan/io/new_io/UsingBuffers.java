package com.ryan.io.new_io;

import com.ryan.util.Util;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;

/**
 * Created by MUFCRyan on 2017/5/10.
 * Book Page561 Chapter 18.10.5 缓冲器的细节
 * Buffer 由数据和可以高效访问及操纵这些数据的四个索引组成，四个索引：mark、position、limit、capacity，只能获取到 position ~ limit
 * 之间的字符，所以操作 Buffer 后要使用 rewind() 将 position 重新置于起始位置
 */

public class UsingBuffers {
    public static void symmetricScramble(CharBuffer buffer){
        while (buffer.hasRemaining()){
            buffer.mark();
            char c1 = buffer.get();
            char c2 = buffer.get();
            buffer.reset();
            buffer.put(c2).put(c1);
        }
    }

    public static void main(String[] args){
        char[] data = "UsingBuffers".toCharArray();
        ByteBuffer buffer = ByteBuffer.allocate(data.length * 2);
        CharBuffer charBuffer = buffer.asCharBuffer();
        charBuffer.put(data);
        Util.println(charBuffer.rewind());
        symmetricScramble(charBuffer);
        Util.println(charBuffer.rewind());
        symmetricScramble(charBuffer);
        Util.println(charBuffer.rewind());
    }
}
