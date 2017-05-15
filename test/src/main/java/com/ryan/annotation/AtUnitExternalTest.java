package com.ryan.annotation;

import com.ryan.io.OSExecute;
import com.ryan.net.mindview.atunit.Test;

/**
 * Created by MUFCRyan on 2017/5/15.
 * Book Page636 Chapter 20.5 基于注解的单元测试
 * 基于继承生成测试类更加灵活简便
 */

public class AtUnitExternalTest extends AtUnitExample1 {
    @Test
    boolean _methodOne(){
        return methodOne().equals("This is methodOne");
    }

    @Test
    boolean _methodTwo(){
        return methodTwo() == 2;
    }

    public static void main(String[] args){
        OSExecute.command("java com.ryan.annotation AtUnitExternalTest");
    }
}
