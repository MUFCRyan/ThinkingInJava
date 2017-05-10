package com.ryan.io.new_io;

import com.ryan.util.Util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.Enumeration;
import java.util.zip.Adler32;
import java.util.zip.CheckedInputStream;
import java.util.zip.CheckedOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

/**
 * Created by MUFCRyan on 2017/5/10.
 * Book Page569 Chapter 18.11.2 使用 Zip 进行多文件保存
 * 两种 Checksum（校验） 类型：Adler32（更快）、CRC32（更准确）
 */

public class ZipCompress {
    public static void main(String[] args) throws IOException{
        String filePath = Util.getSpecifiedFilePath("GZIPCompress.java");
        args = new String[2];
        args[0] = filePath;
        filePath = Util.getSpecifiedFilePath("ZipCompress.java");
        args[1] = filePath;
        FileOutputStream out = new FileOutputStream("test.zip");
        CheckedOutputStream checkedOut = new CheckedOutputStream(out, new Adler32());
        ZipOutputStream zipOut = new ZipOutputStream(checkedOut);
        BufferedOutputStream bufferedOut = new BufferedOutputStream(zipOut);
        zipOut.setComment("A test of Java Zip");
        Util.println("Checksum start: " + checkedOut.getChecksum().getValue());
        // No corresponding getComment(). though
        for (String arg : args) {
            Util.println("Writing file: " + arg);
            BufferedReader bufferedReader = new BufferedReader(new FileReader(arg));
            zipOut.putNextEntry(new ZipEntry(arg)); // 必须调用，将要加入压缩档案的文件传递给下一个 ZipEntry 对象
            int c;
            while ((c = bufferedReader.read()) != -1){
                bufferedOut.write(c);
            }
            bufferedReader.close();
            bufferedOut.flush();
        }
        bufferedOut.close();
        // Checksum valid only after the file has been closed!
        Util.println("Checksum end: " + checkedOut.getChecksum().getValue());
        // Now extract the files
        Util.print("Reading file");
        FileInputStream fileIn = new FileInputStream("test.zip");
        CheckedInputStream checkedIn = new CheckedInputStream(fileIn, new Adler32());
        ZipInputStream zipIn = new ZipInputStream(checkedIn);
        BufferedInputStream bufferedIn = new BufferedInputStream(zipIn);
        ZipEntry zipEntry;
        while ((zipEntry = zipIn.getNextEntry()) != null){
            Util.print("Reading file " + zipEntry);
            int x;
            while ((x = bufferedIn.read()) != -1){
                System.out.write(x);
            }
        }
        if (args.length == 1){
            Util.println("Checksum: " + checkedIn.getChecksum().getValue());
            bufferedIn.close();
            // Alternative way to open and read Zip files
            ZipFile zipFile = new ZipFile("test.zip");
            Enumeration<? extends ZipEntry> entries = zipFile.entries();
            while (entries.hasMoreElements()){
                ZipEntry zipEntry2 = entries.nextElement();
                Util.println("File: " + zipEntry2);
                // ... and extract the data as before
            }
        }
    }
}
