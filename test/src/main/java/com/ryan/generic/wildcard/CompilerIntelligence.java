package com.ryan.generic.wildcard;

import com.ryan.generic.coffee.Capuccino;
import com.ryan.generic.coffee.Coffee;
import com.ryan.generic.coffee.Latte;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by MUFCRyan on 2017/4/11.
 * 编译器只关注入参和返回的对象类型，并不会分析代码以查看是否执行了任何实际的写入和读写操作，无法验证“任何事物”的类型安全性
 */

public class CompilerIntelligence {
    public static void main(String[] args){
        // Wildcard allows covariance
        List<? extends Coffee> cList = new ArrayList<Latte>(); // 此种声明使得我们无法向其中添加任何对象
        Latte coffee = (Latte) cList.get(0);
        boolean contains = cList.contains(new Latte()); // Argument is Object
        int index = cList.indexOf(new Capuccino()); // Argument is Object
    }
}
