package com.ryan.io;

import com.ryan.util.Util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;

/**
 * Created by MUFCRyan on 2017/5/9.
 * Book Page550 Chapter 18.8.3 标准 I/O 重定向
 * System 类提供了相关方法：setIn(InputStream)、setOut/Err(PrintStream)
 */

public class Redirecting {
    public static void main(String[] args) throws IOException {
        String filePath = Util.getSpecifiedFilePath("Redirecting.java");
        PrintStream console = System.out;
        BufferedInputStream in = new BufferedInputStream(new FileInputStream(filePath));
        PrintStream out = new PrintStream(new BufferedOutputStream(new FileOutputStream("test.out")));
        System.setIn(in);
        System.setOut(out);
        System.setErr(out);
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String line;
        while ((line = reader.readLine()) != null){
            Util.println(line);
        }
        out.close();
        System.setOut(console);
    }
}
