package com.ryan.annotation;

import com.ryan.io.OSExecute;
import com.ryan.net.mindview.atunit.Test;
import com.ryan.net.mindview.atunit.TestObjectCleanup;
import com.ryan.net.mindview.atunit.TestObjectCreate;
import com.ryan.net.mindview.atunit.TestProperty;
import com.ryan.util.Util;
import java.io.PrintWriter;

/**
 * Created by MUFCRyan on 2017/5/15.
 * Book Page640 Chapter 20.5 基于注解的单元测试
 */

/**
 * static @TestObjectCleanup：测试对象使用结束后，此方法会自动执行清理工作
 */
public class AtUnitExample5 {
    private String text;
    public AtUnitExample5(String text){
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }

    @TestProperty
    static PrintWriter output;
    @TestProperty static int counter;

    @TestObjectCreate
    static AtUnitExample5 create(){
        String id = Integer.toString(counter++);
        try {
            output = new PrintWriter("Test" + id + ".txt");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return new AtUnitExample5(id);
    }

    @TestObjectCleanup
    static void cleanup(AtUnitExample5 object){
        Util.println("Running cleanup");
        output.close();
    }

    @Test
    boolean test1(){
        output.print("test1");
        return true;
    }

    @Test
    boolean test2(){
        output.print("test2");
        return true;
    }

    @Test
    boolean test3(){
        output.print("test3");
        return true;
    }

    public static void main(String[] args){
        OSExecute.command("java com.ryan.net.mindview.atunit.AtUnit com.ryan.annotation.AtUnitExample5");
    }
}
