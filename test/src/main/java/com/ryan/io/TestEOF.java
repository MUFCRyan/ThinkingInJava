package com.ryan.io;

import com.ryan.util.Util;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;

/**
 * Created by MUFCRyan on 2017/5/9.
 * Book Page541 Chapter 18.6.3 格式化的内存输入 —— available()
 */

public class TestEOF {
    public static void main(String[] args) throws IOException {
        Directory.TreeInfo treeInfo = Directory.walk(".", "TestEOF.java");
        if (treeInfo.files.size() > 0) {
            DataInputStream inputStream = new DataInputStream(new ByteArrayInputStream
                    (BufferedInputFile.read(treeInfo.files.get(1).getPath()).getBytes()));
            while (inputStream.available() != 0)
                Util.print((char)inputStream.readByte());
        }
    }
}
