package com.ryan.io.new_io;

import com.ryan.util.Util;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;

/**
 * Created by MUFCRyan on 2017/5/9.
 * Book Page554 Chapter 18.10.1 转换数据
 */

/**
 * 缓冲器（ByteBuffer）容纳的是普通字节，为了将其转换为字符，要么输入时对其进行编码（输出时才有意义），要么在将其从缓冲器输出时进行解码，可以使用 java.nio.charset
 * .Charset 类实现这些功能 —— 该类提供了把数据编码成多种不同的类型的字符集的工具
 */
public class BufferToText {
    private static final int SIZE = 1024;
    public static void main(String[] args) throws IOException {
        FileChannel channel = new FileOutputStream("data2.txt").getChannel();
        channel.write(ByteBuffer.wrap("Some text".getBytes()));
        channel.close();
        channel = new FileInputStream("data2.txt").getChannel();
        ByteBuffer buffer = ByteBuffer.allocate(SIZE);
        channel.read(buffer);
        buffer.flip();
        // Doesn't work, it's wrong text because of no correct encode
        Util.println(buffer.asCharBuffer());
        // Decode using this system's default Charset
        buffer.rewind(); // 返回到 buffer 的开始部分
        String encoding = System.getProperty("file.encoding");
        Util.println("Decode using: " + encoding + " "+ Charset.forName(encoding).decode(buffer));

        channel = new FileOutputStream("data2.txt").getChannel();
        channel.write(ByteBuffer.wrap("Some text".getBytes("UTF-16BE")));
        channel.close();
        // Try reading again
        channel = new FileInputStream("data2.txt").getChannel();
        buffer.clear();
        channel.read(buffer);
        buffer.flip();
        // Now it's correct because of has the right encode duration the write
        Util.println(buffer.asCharBuffer());

        // Use a CharBuffer to write through
        channel = new FileOutputStream("data2.txt").getChannel();
        buffer = ByteBuffer.allocate(24);
        buffer.asCharBuffer().put("Some text");// It's works even if it does not use the encode
        channel.write(buffer);
        channel.close();
        channel = new FileInputStream("data2.txt").getChannel();
        buffer.clear();
        channel.read(buffer);
        buffer.flip();
        Util.println(buffer.asCharBuffer());
    }
}
