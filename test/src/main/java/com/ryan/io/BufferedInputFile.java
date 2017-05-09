package com.ryan.io;

import com.ryan.util.Util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by MUFCRyan on 2017/5/9.
 * Book Page539 Chapter 18.6 I/O 流的典型使用方式
 * Book Page540 Chapter 18.6.1 缓冲输入文件
 */

public class BufferedInputFile {
    public static String read(String fileName) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(fileName));
        String line;
        StringBuilder builder = new StringBuilder();
        while ((line = reader.readLine()) != null){
            builder.append(line + "\n"); // readLine 会将换行符去掉
        }
        reader.close();
        return builder.toString();
    }

    public static void main(String[] args) throws IOException {
        Directory.TreeInfo treeInfo = Directory.walk(".", "BufferedInputFile.java");
        if (treeInfo.files.size() > 0){
            Util.println(read(treeInfo.files.get(1).getPath()));
        }
    }
}
