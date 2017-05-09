package com.ryan.io;

import com.ryan.util.Util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringReader;

/**
 * Created by MUFCRyan on 2017/5/9.
 * Book Page542 Chapter 18.6.4 基本文件输出
 */

public class BasicFileOutput {
    static String file = "BasicFileOutput.out";
    public static void main(String[] args) throws IOException {
        String filePath = Util.getSpecifiedFilePath("BasicFileOutput.java");
        BufferedReader reader = new BufferedReader(new StringReader(BufferedInputFile.read(filePath)));
        PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(file)));
        int lineCount = 1;
        String line;
        while ((line = reader.readLine()) != null){
            out.println(lineCount++ + ": " + line);
        }
        out.close();
        // Show the stored file
        Util.println(BufferedInputFile.read(file));
    }
}
