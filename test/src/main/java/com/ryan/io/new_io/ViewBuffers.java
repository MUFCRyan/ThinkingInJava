package com.ryan.io.new_io;

import com.ryan.util.Util;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.CharBuffer;
import java.nio.DoubleBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.LongBuffer;
import java.nio.ShortBuffer;
import java.util.Arrays;

/**
 * Created by MUFCRyan on 2017/5/10.
 * Book Page558 Chapter 18.10.3 视图缓冲器（view buffer）：可以把任何数据转化为某一特定的基本类型
 */

public class ViewBuffers {
    public static void main(String[] args){
        ByteBuffer buffer = ByteBuffer.wrap(new byte[]{0, 0, 0, 0, 0, 0, 0, 'a'});
        buffer.rewind();
        Util.print("ByteBuffer: ");
        while (buffer.hasRemaining()){
            Util.print(buffer.position() + " --> " + buffer.get() + ", ");
        }
        Util.println();
        CharBuffer charBuffer = ((ByteBuffer) buffer.rewind()).asCharBuffer();
        Util.print("Char Buffer: ");
        while (charBuffer.hasRemaining()){
            Util.print(charBuffer.position() + " --> " + charBuffer.get() + ", ");
        }
        Util.println();
        IntBuffer intBuffer = ((ByteBuffer) buffer.rewind()).asIntBuffer();
        Util.print("Int Buffer: ");
        while (intBuffer.hasRemaining()){
            Util.print(intBuffer.position() + " --> " + intBuffer.get() + ", ");
        }
        Util.println();
        ShortBuffer shortBuffer = ((ByteBuffer) buffer.rewind()).asShortBuffer();
        Util.print("Short Buffer: ");
        while (shortBuffer.hasRemaining()){
            Util.print(shortBuffer.position() + " --> " + shortBuffer.get() + ", ");
        }
        Util.println();
        LongBuffer longBuffer = ((ByteBuffer) buffer.rewind()).asLongBuffer();
        Util.print("Long Buffer: ");
        while (longBuffer.hasRemaining()){
            Util.print(longBuffer.position() + " --> " + longBuffer.get() + ", ");
        }
        Util.println();
        FloatBuffer floatBuffer = ((ByteBuffer) buffer.rewind()).asFloatBuffer();
        Util.print("Float Buffer: ");
        while (floatBuffer.hasRemaining()){
            Util.print(floatBuffer.position() + " --> " + floatBuffer.get() + ", ");
        }
        Util.println();
        DoubleBuffer doubleBuffer = ((ByteBuffer) buffer.rewind()).asDoubleBuffer();
        Util.print("Double Buffer: ");
        while (doubleBuffer.hasRemaining()){
            Util.print(doubleBuffer.position() + " --> " + doubleBuffer.get() + ", ");
        }
        Util.println();
    }
}

/**
 * 多字节数据存放时要考虑字节存放次序 —— 高位优先（big endian）、低位优先（little endian），默认是高位优先
 * 高/低位优先即：最重要的字节放在地址最低/高的存储单元；相关常量：ByteOrder.BIG_ENDIAN、LITTLE_ENDIAN
 * 次序改变时是各字节内部全部反转，相当于按反向顺序重新排列
 */
class Endians{
    public static void main(String[] args){
        ByteBuffer buffer = ByteBuffer.wrap(new byte[8]);
        buffer.asIntBuffer().put(1234567890);
        Util.println(Arrays.toString(buffer.array()));
        Util.println(buffer.getInt());
        buffer.rewind();
        buffer.order(ByteOrder.BIG_ENDIAN);
        buffer.asLongBuffer().put(1234567890);
        Util.println(Arrays.toString(buffer.array()));
        Util.println(buffer.getInt());
        buffer.rewind();
        buffer.order(ByteOrder.LITTLE_ENDIAN);
        buffer.asLongBuffer().put(1234567890);
        Util.println(Arrays.toString(buffer.array()));
        Util.println(buffer.getInt());
    }
}
