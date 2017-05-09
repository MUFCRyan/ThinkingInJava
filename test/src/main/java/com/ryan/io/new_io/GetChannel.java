package com.ryan.io.new_io;

import com.ryan.util.Util;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * Created by MUFCRyan on 2017/5/9.
 * Book Page552 Chapter 18.10 新 I/O
 * 速度的提升来自于所使用的结构更接近于操作系统执行 I/O 的方式：通道和缓冲器
 * FileInput/OutputStream 和 RandomAccessFile 可以产生 FileChannel，java.nio.channels.Channels
 * 中有实用方法用以在通道中产生字符模式类 Reader、Writer
 */

public class GetChannel {
    private static final int SIZE = 1024;
    public static void main(String[] args) throws IOException{
        FileChannel channel = new FileOutputStream("data.txt").getChannel();
        channel.write(ByteBuffer.wrap("Some text ".getBytes())); // wrap() 方式不再复制底层数组，而是将其作为所产生的ByteBuffer 的存储器 —— 数组支持的 ByteBuffer
        channel.close();
        // Add to the end of life
        channel = new RandomAccessFile("data.txt", "rw").getChannel();
        channel.position(channel.size());
        channel.write(ByteBuffer.wrap("Some more".getBytes()));
        channel.close();
        // Read the file
        channel = new FileInputStream("data.txt").getChannel();
        ByteBuffer byteBuffer = ByteBuffer.allocate(SIZE);
        channel.read(byteBuffer);
        // read() 之后必须调用 flip() 用以做好让别人读取的准备，执行进一步的 read() 操作之前必须为每个 read() 调用 clear()，实例查看 ChannelCopy
        byteBuffer.flip();
        while (byteBuffer.hasRemaining())
            Util.print((char)byteBuffer.get());
    }
}
