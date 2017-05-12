package com.ryan.enumerated;

import com.ryan.util.Util;

import java.text.DateFormat;
import java.util.Date;

/**
 * Created by MUFCRyan on 2017/5/12.
 * Book Page603 Chapter 19.10 常量相关方法
 * enum 特性：允许为其实例编写相关方法 --> 从而为每个 enum 实例赋予各自不同的行为；方式：为 enum 实例定义数个 abstract 方法 --> 然后为每个 enum
 * 实例实现该抽象方法
 */

/**
 * 表驱动代码（table driven code）：通过相应的 enum 实例，我们可以调用其上的方法；enum 也能体现出多态的行为，类似 class，但是不能将其真正的 class 使用
 */
public enum ConstantSpecificMethod {
    DATE_TIME{
        String getInfo(){
            return DateFormat.getDateInstance().format(new Date());
        }
    },
    CLASSPATH{
        String getInfo(){
            return System.getenv("CLASSPATH");
        }
    },
    VERSION{
        String getInfo(){
            return System.getProperty("java.version");
        }
    };
    abstract String getInfo();
    public static void main(String[] args){
        for (ConstantSpecificMethod method : values()) {
            Util.println(method.getInfo());
        }
    }
}
