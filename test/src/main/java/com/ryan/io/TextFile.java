package com.ryan.io;

/**
 * !!IMPORTANT
 * Created by MUFCRyan on 2017/5/9.
 * Book Page546 Chapter 18.7 文件读写的实用工具
 */

import com.ryan.util.Util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.TreeSet;

/**
 * Static functions for reading and writing text files as a single string, and treating a file as
 * an ArrayList
 * 另一种解决方案是使用 java.util.Scanner 类，但其只能读取文件，不能写入，且此工具主要用于创建编程语言的扫描器或“小语言”
 */
public class TextFile extends ArrayList<String>{
    /** Read a file as a single string */
    public static String read(String fileName){
        StringBuilder sb = new StringBuilder();
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(new File(fileName).getAbsoluteFile()));
            try {
                String line;
                while ((line = reader.readLine()) != null){
                    sb.append(line);
                    sb.append("\n");
                }
            } finally {
                reader.close();
            }
        } catch (IOException e){
            throw new RuntimeException(e);
        }
        return sb.toString();
    }

    /** Write a single file in one method call */
    public static void write(String fileName, String text){
        try {
            PrintWriter writer = new PrintWriter(new File(fileName).getAbsoluteFile());
            try {
                writer.println(text);
            } finally {
                writer.close();
            }
        } catch (IOException e){
            throw new RuntimeException(e);
        }
    }

    /** Read a file, split by any regular expression */
    public TextFile(String fileName, String splitter){
        super(Arrays.asList(read(fileName).split(splitter)));
        // Regular expression split() often leaves an empty String at the first position
        if (get(0).equals(""))
            remove(0);
    }

    /** Normally read by lines */
    public TextFile(String fileName){
        this(fileName, "\n");
    }

    public void write(String fileName){
        try {
            PrintWriter writer = new PrintWriter(new File(fileName).getAbsoluteFile());
            try {
                for (String item : this) {
                    writer.println(item);
                }
            } finally {
                writer.close();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args){
        String filePath = Util.getSpecifiedFilePath("TextFile.java");
        write("test.txt", filePath);
        TextFile text = new TextFile("test.txt");
        text.write("test2.txt");
        // Break into unique sorted list of words
        TreeSet<String> words = new TreeSet<>(new TextFile(filePath, "\\W+"));
        // Display the words
        Util.println(words.headSet("a"));
    }
}
