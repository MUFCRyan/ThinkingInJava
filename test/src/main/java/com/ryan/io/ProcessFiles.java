package com.ryan.io;

import com.ryan.util.Util;

import java.io.File;
import java.io.IOException;

/**
 * Created by MUFCRyan on 2017/5/9.
 * Book Page531 Chapter 18.1.2 目录使用工具
 * 根据 Strategy 处理目录中的文件
 */

/**
 * Strategy 内嵌在 ProcessFiles 中，ProcessFiles 执行了查找具有特定扩展名的文件所需的全部工作，当其找到匹配文件时将直接把文件传递给 Strategy
 *      对象，在该对象的 process 方法中 可以执行特定处理
 */
public class ProcessFiles {
    public interface Strategy {
        void process(File file);
    }

    private Strategy mStrategy;
    private String mExt;

    public ProcessFiles(Strategy strategy, String ext) {
        mStrategy = strategy;
        mExt = ext;
    }

    public void start(String[] args) {
        try {
            if (args.length == 0)
                processDirectoryTree(new File("."));
            else {
                for (String path : args) {
                    File file = new File(path);
                    if (file.isDirectory())
                        processDirectoryTree(file);
                    else {
                        if (!path.endsWith("." + mExt))
                            path += "." + mExt;
                        mStrategy.process(new File(path).getCanonicalFile());
                    }
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void processDirectoryTree(File root) throws IOException {
        Directory.TreeInfo tree = Directory.walk(root.getAbsolutePath(), ".*\\." + mExt);
        for (File file : tree) {
            mStrategy.process(file.getCanonicalFile());
        }
    }

    // Demonstration of how to use it
    public static void main(String[] args){
        new ProcessFiles(new Strategy() {
            @Override
            public void process(File file) {
                Util.println(file);
            }
        }, "java").start(args);
    }
}
