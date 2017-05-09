package com.ryan.io;

import com.ryan.util.Util;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.EOFException;
import java.io.IOException;

/**
 * Created by MUFCRyan on 2017/5/9.
 * Book Page541 Chapter 18.6.3 格式化的内存输入
 */

/**
 * 从 InputStream 中逐个读取的字节都是合法结果，其返回的字节不能用于判断，应使用 available() 检测
 */
public class FormattedMemoryInput {
    public static void main(String[] args) throws IOException {
        try {
            Directory.TreeInfo treeInfo = Directory.walk(".", "FormattedMemoryInput.java");
            if (treeInfo.files.size() > 0) {
                DataInputStream inputStream = new DataInputStream(new ByteArrayInputStream
                        (BufferedInputFile.read(treeInfo.files.get(1).getPath()).getBytes()));
                while (true)
                    Util.print((char)inputStream.readByte());
            }
        } catch (EOFException e){
            System.err.println("End of Stream");
        }
    }
}
