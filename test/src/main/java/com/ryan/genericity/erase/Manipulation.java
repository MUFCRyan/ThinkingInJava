package com.ryan.genericity.erase;

import com.ryan.util.Util;

/**
 * Created by MUFCRyan on 2017/4/6.
 * 类型信息擦除的影响和解决方式
 * 注意：只有当希望代码能够跨多个类工作时使用泛型才有帮助，否则使用普通类即可；必须查看所有代码以确定其是否足够复杂到必须使用泛型的程度
 */

public class Manipulation {
    public static void main(String[] args){
        HasF hasF = new HasF();
        Manipulator<HasF> manipulator = new Manipulator<>(hasF);
        manipulator.manipulate();
    }
}

class HasF{
    public void f(){
        Util.println("HasF.f()");
    }
}

class Manipulator<T> {
    private T obj;
    public Manipulator(T x){
        obj = x;
    }
    public void manipulate(){
        //obj.f(); //cannot find method f()
    }
}

class Manipulator2<T extends HasF> {
    private T obj;
    public Manipulator2(T x){
        obj = x;
    }
    public void manipulate(){
        obj.f(); //Because of limited generic type to HasF and the derived classes so it can findmethod f()
    }
}
