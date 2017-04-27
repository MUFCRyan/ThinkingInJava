package com.ryan.generic.self_bounded;

/**
 * Created by MUFCRyan on 2017/4/27.
 * Book Page404 Chapter 15.12.2 自限定
 * 自限定参数的意义：保证类型参数与正在被定义的类相同，只能强制作用于继承关系
 */

class Other{}

/**
 * BasicHolder 可以使用任何泛型作为其参数
 */
class BasicOther extends BasicHolder<Other>{}

public class Unconstrained {
    public static void main(String[] args){
        BasicOther other1 = new BasicOther(), other2 = new BasicOther();
        other1.set(new Other());
        Other other = other1.get();
        other1.f();
    }
}

// -------------------------------------------------------------------------------------
class SelfBounded<T extends SelfBounded<T>>{
    T element;
    SelfBounded<T> set(T arg){
        element = arg;
        return this;
    }
    T get(){
        return element;
    }
}

class A extends SelfBounded<A>{}

class B extends SelfBounded<A>{}

class C extends SelfBounded<C>{
    C setAndGet(C arg){
        set(arg);
        return get();
    }
}

class D {}
//此处 D 未继承 SelfBounded<D> 导致此种用法不能像 B 一样在 E 上生效，自限定只能强制作用于继承关系
// class E extends SelfBounded<D>{}

class F extends SelfBounded{}

class SelfBounding{
    public static void main(String[] args){
        A a = new A();
        a.set(new A());
        a = a.set(new A()).get();
        a = a.get();
        C c = new C();
        c = c.setAndGet(new C());
    }
}
// -------------------------------------------------------------------------------------
// 自限定用于泛型，可以防止此方法被应用于除此类形式的自限定参数之外的任何事物上
class SelfBoundedMethods{
    static <T extends SelfBounded<T>> T f(T arg){
        return arg.set(arg).get();
    }

    public static void main(String[] args){
        A a = f(new A());
    }
}

