package com.ryan.io.new_io;

import com.ryan.util.Util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

/**
 * Created by MUFCRyan on 2017/5/10.
 * Book Page568 Chapter 18.11 压缩：相关类派生于 Input/OutputStream，常用的压缩算法是 GZIP 和 Zip，可以压缩任何东西包括网络传输的数据
 * Book Page568 Chapter 18.11.1 使用 GZIP 进行简单压缩：使用上 直接将输出流封装为
 * GZIPOutputStream/ZipOutputStream，将输入流封装为 GZIPInputStream/ZipInputStream 即可，其它的就是通常的 I/O 操作
 */

public class GZIPCompress {
    public static void main(String[] args) throws IOException{
        String filePath = Util.getSpecifiedFilePath("GZIPCompress.java");
        args = new String[1];
        args[0] = filePath;
        if (args.length == 0) {
            Util.println("Usage: \nGZIPCompress file\n" + "\tUsers GZIP compression to compress the file to test.gz");
            System.exit(1);
        }

        BufferedReader reader = new BufferedReader(new FileReader(args[0]));
        BufferedOutputStream out = new BufferedOutputStream(new GZIPOutputStream(new FileOutputStream("test.gz")));
        Util.println("Writing file");
        int c;
        while ((c = reader.read()) != -1){
            out.write(c);
        }
        reader.close();
        out.close();

        Util.println("Reading file");
        BufferedInputStream in = new BufferedInputStream(new GZIPInputStream(new FileInputStream("test.gz")));
        while ((c = in.read()) != -1){
            Util.print(c);
        }
        in.close();
    }
}
