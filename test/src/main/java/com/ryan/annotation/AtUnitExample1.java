package com.ryan.annotation;

import com.ryan.io.OSExecute;
import com.ryan.net.mindview.atunit.Test;
import com.ryan.util.Util;

/**
 * Created by MUFCRyan on 2017/5/15.
 * Book Page635 Chapter 20.5 基于注解的单元测试：测试类必须有 package 声明
 */

public class AtUnitExample1 {
    public String methodOne(){
        return "This is methodOne";
    }

    public int methodTwo(){
        Util.println("This is methodTwo");
        return 2;
    }

    @Test
    boolean methodOneTest(){
        return methodOne().equals("This is methodOne");
    }

    @Test
    boolean m2(){
        return methodTwo() == 2;
    }

    @Test
    private boolean m3(){
        return true;
    }

    // Shows output for failure
    @Test
    boolean failureTest(){
        return false;
    }

    @Test
    boolean anotherDisappointment(){
        return false;
    }

    public static void main(String[] args){
        OSExecute.command("java com.ryan.annotation AtUnitExample1");
    }
}
