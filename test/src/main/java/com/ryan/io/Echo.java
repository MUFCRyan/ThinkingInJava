package com.ryan.io;

import com.ryan.util.Util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by MUFCRyan on 2017/5/9.
 * Book Page548 Chapter 18.8 标准 I/O，此概念来自于 Unix 中的“程序所使用的单一信息流” ——
 *      程序中的所有输入可以来自于标准输入，所有输出可以发送到标准输出，所有错误可以发送到标准错误；
 *      意义：很容易将程序串联起来，一个程序的标准输入可以成为另一个程序的标准输出
 * Java 提供了 System.in/out/err
 *
 * Book Page548 Chapter 18.8.1 从标准输入中读取
 */

public class Echo {
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String line;
        while ((line = reader.readLine()) != null && line.length() != 0)
            Util.println(line);
    }
}
