package com.ryan.io;

import com.ryan.util.Util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by MUFCRyan on 2017/5/9.
 * Book Page543 Chapter 18.6.5 存储和恢复数据
 */

/**
 * DataOutput/InputStream 保证了读写的跨平台正确性，write/readUTF() 使用的是适合于 Java 的 UTF-8 变体，非 Java 程序读取时需要特殊处理
 * 写入顺序和读取顺序要匹配，否则可能会将类型读错 --> 所以要么为文件中的数据采用固定格式；要么将额外信息保存到文件中，以便能够对其进行解析以确定数据的存放位置
 */
public class StoringAndRecoveringData {
    public static void main(String[] args) throws IOException {
        DataOutputStream out = new DataOutputStream(new BufferedOutputStream(new FileOutputStream("Data.txt")));
        out.writeDouble(3.14159);
        out.writeUTF("That was pi");
        out.writeDouble(1.41413);
        out.writeUTF("Square root of 2");
        out.close();
        DataInputStream in = new DataInputStream(new BufferedInputStream(new FileInputStream("Data.txt")));
        Util.println(in.readDouble());
        Util.println(in.readUTF()); // Only readUTF() will recover the Java-UTF string properly
        Util.println(in.readDouble());
        Util.println(in.readUTF());
    }
}
