package com.ryan.genericity.tuple;

import com.ryan.genericity.coffee.Ameicano;
import com.ryan.genericity.coffee.Breve;
import com.ryan.genericity.coffee.Capuccino;
import com.ryan.genericity.coffee.Coffee;
import com.ryan.genericity.coffee.Latte;
import com.ryan.util.Util;

/**
 * Created by MUFCRyan on 2017/4/5.
 * Tuple测试
 */

public class TupleTest {
    static TwoTuple<String, Integer> f(){
        return Tuple.tuple("hello", 17);
    }

    static TwoTuple f2(){
        return Tuple.tuple("hi", 15);
    }

    static ThreeTuple<Coffee, String, Integer> g(){
        return Tuple.tuple(new Latte(), "Latte", 5);
    }

    static FourTuple<Coffee, Coffee, String, Integer> h(){
        return Tuple.tuple(new Capuccino(), new Breve(), "Monica", 4);
    }

    static FiveTuple<Coffee, Coffee, String, Integer, Double> k(){
        return Tuple.tuple(new Ameicano(), new Latte(), "Karen", 7, 2.5d);
    }

    static SixTuple<Coffee, Coffee, String, Integer, Double, Boolean> j(){
        return Tuple.tuple(new Ameicano(), new Latte(), "Karen", 7, 2.5d, true);
    }

    public static void main(String[] args){
        TwoTuple<String, Integer> tuple = f();
        Util.println(tuple.toString());
        Util.println(f2().toString());
        Util.println(g().toString());
        Util.println(h().toString());
        Util.println(k().toString());
        Util.println(j().toString());
    }
}
