package com.ryan.io.new_io;

import com.ryan.util.Util;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

/**
 * !!IMPORTANT
 * Created by MUFCRyan on 2017/5/10.
 * Book Page563 Chapter 18.10.6
 * 内存映射文件：允许创建、修改因为体积过大而无法放入内存中的文件，通过内存映射文件就可以假定整个文件都放在内存中，而且可以完全完全将其看作很大的数据来进行访问 -->
 *      极大地简化了用于修改文件的代码；速度相比 I/O 快很多的原因是只建立了数据和程序私有控件的映射关系，省去了将数据拷贝到 OS 内核缓冲区的操作
 */

public class LargeMappingFiles {
    static int length = 0x8FFFFFF; // 128MB
    public static void main(String[] args) throws IOException {
        MappedByteBuffer out = new RandomAccessFile("test.dat", "rw").getChannel().map(FileChannel
                        .MapMode.READ_WRITE, 0, length);
        ByteBuffer buffer = ByteBuffer.allocate(length);
        long start = System.currentTimeMillis();
        for (int i = 0; i < length; i++) {
            out.put((byte) 'x');
        }
        long end = System.currentTimeMillis();
        Util.println("Mapping File Spend time: " + (end - start));
        long start2 = System.currentTimeMillis();
        for (int i = 0; i < length; i++) {
            buffer.put((byte) 'x');
        }
        long end2 = System.currentTimeMillis();
        Util.println("Buffer Spend time: " + (end2 - start2));
        Util.println("Finishing Writing");
        for (int i = length / 2; i < length / 2 + 6; i++) {
            Util.println((char)out.get(i));
        }
    }
}
