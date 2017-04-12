package com.ryan.genericity.erase;

/**
 * Created by MUFCRyan on 2017/4/6.
 *
 */
class GenericBase<T> {
    private T element;
    public void set(T arg){
        arg = element;
    }
    public T get(){
        return element;
    }
}

class Derived1<T> extends GenericBase<T> {

}

class Derived2<T> extends GenericBase<T> {}

public class ErasureAndInheritance {
    @SuppressWarnings("unchecked")
    public static void main(String[] args){
        Derived2 derived2 = new Derived2();
        Object o = derived2.get();
        derived2.set(o);
    }
}