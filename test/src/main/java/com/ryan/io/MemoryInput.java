package com.ryan.io;

import com.ryan.util.Util;

import java.io.IOException;
import java.io.StringReader;

/**
 * Created by MUFCRyan on 2017/5/9.
 * Book Page541 Chapter 18.6.2 从内存输入
 */

public class MemoryInput {
    public static void main(String[] args) throws IOException {
        Directory.TreeInfo treeInfo = Directory.walk(".", "MemoryInput.java");
        if (treeInfo.files.size() > 0){
            StringReader reader = new StringReader(BufferedInputFile.read(treeInfo.files.get(1).getPath()));
            int c;
            while ((c = reader.read()) != -1){
                Util.print((char)c);
            }
        }
    }
}
