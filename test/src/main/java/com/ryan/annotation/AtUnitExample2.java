package com.ryan.annotation;

import com.ryan.io.OSExecute;
import com.ryan.net.mindview.atunit.Test;
import com.ryan.util.Util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Created by MUFCRyan on 2017/5/15.
 * Book Page637 Chapter 20.5 基于注解的单元测试
 * @Unit 中没有 JUnit 中的 assert 方法；@Unit 的设计目标之一即是尽可能少地添加额外的语法；@Unit 即是遇到失败的测试也会继续运行直至所有测试方法测试完毕
 */

public class AtUnitExample2 {
    public String methodOne(){
        return "This is methodOne";
    }

    public int methodTwo(){
        Util.println("This is methodTwo");
        return 2;
    }

    @Test
    void assertExample(){
        assert methodOne().equals("This is methodTwo");
    }

    @Test
    void assertFailureExample(){
        assert 1 == 2 : "What a surprise!";
    }

    @Test
    void exceptionExample() throws IOException {
        new FileInputStream("nofile.txt");
    }

    @Test
    boolean assertAndReturn(){
        // Assertion with message
        assert methodTwo() == 2 : "methodTwo must equals 2";
        return methodOne().equals("This is methodTwo");
    }

    public static void main(String[] args){
        OSExecute.command("java com.ryan.net.mindview.atunit.AtUnit com.ryan.annotation.AtUnitExample2");
    }
}
