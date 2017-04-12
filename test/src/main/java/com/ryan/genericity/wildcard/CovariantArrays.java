package com.ryan.genericity.wildcard;

import com.ryan.util.Util;

/**
 * Created by MUFCRyan on 2017/4/6.
 * 数组的特殊行为：可以向导出类型的数组赋予基类型的数组引用
 */

class Fruit {
}

class Apple extends Fruit {
}

class Jonathon extends Apple {
}

class Orange extends Fruit {
}

public class CovariantArrays {
    public static void main(String[] args) {
        Fruit[] fruit = new Fruit[10];
        fruit[0] = new Apple();
        fruit[1] = new Jonathon();
        try {
            fruit[1] = new Fruit();
        } catch (Exception e){
            Util.println("e1 : " + e);
        }

        try {
            fruit[1] = new Orange();
        } catch (Exception e){
            Util.println("e2 : " + e);
        }
    }
}
