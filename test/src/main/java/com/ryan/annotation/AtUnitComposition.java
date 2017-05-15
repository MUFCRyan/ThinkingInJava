package com.ryan.annotation;

import com.ryan.io.OSExecute;
import com.ryan.net.mindview.atunit.Test;

/**
 * Created by MUFCRyan on 2017/5/15.
 * Book Page636 Chapter 20.5 基于注解的单元测试
 */

public class AtUnitComposition {
    AtUnitExample1 mExample = new AtUnitExample1();
    @Test
    boolean _methodOne(){
        return mExample.methodOne().equals("This is methodOne");
    }

    @Test
    boolean _methodTwo(){
        return mExample.methodTwo() == 2;
    }

    public static void main(String[] args){
        OSExecute.command("java com.ryan.annotation AtUnitComposition");
    }
}
