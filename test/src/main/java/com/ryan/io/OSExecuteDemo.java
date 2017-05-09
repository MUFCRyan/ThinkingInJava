package com.ryan.io;

import com.ryan.util.Util;

/**
 * Created by MUFCRyan on 2017/5/9.
 * Book Page551 Chapter 18.9 进程控制
 */

public class OSExecuteDemo {
    public static void main(String[] args){
        String filePath = Util.getSpecifiedFilePath("OSExecuteDemo.java");
        OSExecute.command("javap " + filePath);
    }
}
