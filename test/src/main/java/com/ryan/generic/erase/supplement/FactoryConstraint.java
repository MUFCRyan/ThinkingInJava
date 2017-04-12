package com.ryan.generic.erase.supplement;

/**
 * Created by MUFCRyan on 2017/4/6.
 * 显式工厂 !!IMPORTANT
 */

interface FactoryI<T> {
    T create();
}

class Foo2<T> {
    private T x;
    public <F extends FactoryI<T>> Foo2(F factory){
        x = factory.create();
    }
}

class IntegerFactory implements FactoryI<Integer> {

    @Override
    public Integer create() {
        return new Integer(0);
    }
}

class Widget {
    public static class Factory implements FactoryI<Widget> {

        @Override
        public Widget create() {
            return new Widget();
        }
    }
}

public class FactoryConstraint {
    public static void main(String[] args){
        new Foo2<>(new IntegerFactory());
        new Foo2<>(new Widget.Factory());
    }
}
