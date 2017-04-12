package com.ryan.generic.wildcard;

import com.ryan.generic.coffee.Coffee;
import com.ryan.generic.coffee.Latte;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by MUFCRyan on 2017/4/11.
 * 通配符允许在两个类型之间建立某种类型的向上转型的关系
 */

public class GenericsAndCovariance {
    public static void main(String[] args){
        // Wildcard allows covariance
        List<? extends Coffee> cList = new ArrayList<Latte>(); // 此种声明使得我们无法向其中添加任何对象
        // Compile Error: can't add any type of Object
        /*cList.add(new Latte());
        cList.add(new Fruit());
        cList.add(new Object());*/

        cList.add(null); // It's OK but insignificance
        Coffee coffee = cList.get(0);
    }
}
