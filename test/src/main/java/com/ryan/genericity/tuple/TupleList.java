package com.ryan.genericity.tuple;

import com.ryan.genericity.coffee.Coffee;
import com.ryan.util.Util;

import java.util.ArrayList;

/**
 * Created by MUFCRyan on 2017/4/5.
 * 泛型可以简单、安全构架复杂模型，该类为相应的Demo
 */

public class TupleList<A, B, C, D> extends ArrayList<FourTuple<A, B, C, D>>{
    public static void main(String[] args){
        TupleList<Coffee, Coffee, String, Integer> list = new TupleList<>();
        list.add(TupleTest.h());
        list.add(TupleTest.h());
        for (FourTuple<Coffee, Coffee, String, Integer> tuple : list) {
            Util.println(tuple);
        }
    }
}
