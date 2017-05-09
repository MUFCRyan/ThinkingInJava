package com.ryan.io;

import com.ryan.util.Util;
import java.io.IOException;
import java.io.RandomAccessFile;


/**
 * Created by MUFCRyan on 2017/5/9.
 * Book Page544 Chapter 18.6.6 读写随机访问文件
 * 除了实现 DataInput/Output 接口外有效地与 I/O 继承层次结构的其它部分进行了分离，不支持装饰
 */

public class UsingRandomAccessFile {
    static String file = "RandomAccessFileTest.dat";
    static void disply() throws IOException {
        RandomAccessFile accessFile = new RandomAccessFile(file, "r"); // "r" 代表以只读方式使用
        for (int i = 0; i < 7; i++) {
            Util.println("Value " + i + ": " + accessFile.readDouble());
        }
        Util.println(accessFile.readUTF());
        accessFile.close();
    }

    public static void main(String[] args) throws IOException {
        RandomAccessFile accessFile = new RandomAccessFile(file, "rw");
        for (int i = 0; i < 7; i++) {
            accessFile.writeDouble(i * 1.414);
        }
        accessFile.writeUTF("The end of the file");
        accessFile.close();
        disply();
        accessFile = new RandomAccessFile(file, "rw");
        accessFile.seek(5 * 8); // 移动是逐字节的，一个 double 占8字节，所以此处移动的是5个 double
        accessFile.writeDouble(47.0001);
        accessFile.close();
        disply();
    }
}
