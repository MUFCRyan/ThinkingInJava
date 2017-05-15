package com.ryan.annotation;

import com.ryan.annotation.custom.Test;
import com.ryan.util.Util;

/**
 * Created by MUFCRyan on 2017/5/15.
 * Book Page620 Chapter 20 注解（元数据）：为在代码中添加信息提供了一种形式化的方法，使得可在稍后某个时刻非常方便的使用这些数据
 *
 * Book Page620 Chapter 20.1 基本语法，注意注解不支持继承
 */

public class Testable {
    public void execute(){
        Util.println("Executing...");
    }

    @Test
    void testExecute(){
        execute();
    }
}
