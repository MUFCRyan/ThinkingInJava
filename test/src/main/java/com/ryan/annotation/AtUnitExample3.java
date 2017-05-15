package com.ryan.annotation;

import com.ryan.io.OSExecute;
import com.ryan.net.mindview.atunit.Test;
import com.ryan.net.mindview.atunit.TestObjectCreate;
import com.ryan.util.Util;

/**
 * Created by MUFCRyan on 2017/5/15.
 * Book Page638 Chapter 20.5 基于注解的单元测试
 */

public class AtUnitExample3 {
    private int n;
    public AtUnitExample3(int n){
        this.n = n;
    }
    public int getN(){
        return n;
    }
    public String methodOne(){
        return "This is methodOne";
    }

    public int methodTwo(){
        Util.println("This is methodTwo");
        return 2;
    }

    @TestObjectCreate
    static AtUnitExample3 create(){
        return new AtUnitExample3(47);
    }

    @Test
    boolean initialization(){
        return n == 47;
    }

    @Test
    boolean methodOneTest(){
        return methodOne().equals("This is methodOne");
    }

    @Test
    boolean m2(){
        return methodTwo() == 2;
    }

    public static void main(String[] args){
        OSExecute.command("java net.mindview.atunit.AtUnit AtUnitExample3");
    }
}
