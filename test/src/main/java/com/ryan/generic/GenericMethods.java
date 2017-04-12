package com.ryan.generic;

import com.ryan.util.Util;

/**
 * Created by MUFCRyan on 2017/4/5.
 * 泛型方法
 */

public class GenericMethods {
    private <T> void f(T x){
        Util.println(x.getClass().getName());
    }

    public static void main(String[] args){
        GenericMethods methods = new GenericMethods();
        methods.f("");
        methods.f(1);
        methods.f(false);
        methods.f(1.536);
        methods.f(1.4f);
        methods.f(methods);
    }
}
