package com.ryan.genericity.erase.supplement;

import com.ryan.util.Util;

/**
 * Created by MUFCRyan on 2017/4/6.
 * 设计模式——模板方法
 */

abstract class GenericWithCreate<T> {
    final T element;
    GenericWithCreate(){
        element = create();
    }
    abstract T create();
}

class X {}

class Creator extends GenericWithCreate<X> {

    @Override
    X create() {
        return new X();
    }

    void f(){
        Util.println(element.getClass().getSimpleName());
    }
}

public class CreatorGeneric {
    public static void main(String[] args){
        Creator creator = new Creator();
        creator.f();
    }
}
