package com.ryan.io;

import com.ryan.util.Util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringReader;

/**
 * Created by MUFCRyan on 2017/5/9.
 * Book Page542 Chapter 18.6.4 文本文件输出的快捷方式
 */

public class FileOutputShortcut {
    static String file = "FileOutputShortcut.out";
    public static void main(String[] args) throws IOException {
        String filePath = Util.getSpecifiedFilePath("FileOutputShortcut.java");
        BufferedReader reader = new BufferedReader(new StringReader(BufferedInputFile.read(filePath)));
        // Here's the shortcut
        PrintWriter out = new PrintWriter(file);
        int lineCount = 1;
        String line;
        while ((line = reader.readLine()) != null)
            out.println(lineCount + ": " + line);
        out.close();
        // Show the stored file
        Util.println(BufferedInputFile.read(file));
    }
}
