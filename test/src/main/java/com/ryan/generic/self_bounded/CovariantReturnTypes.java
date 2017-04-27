package com.ryan.generic.self_bounded;

import com.ryan.util.Util;

/**
 * Created by MUFCRyan on 2017/4/27.
 * Book Page404 Chapter 15.12.3 参数协变
 * 自限定类型的价值之一：可以产生协变参数类型 —— 方法参数类型、返回参数类型随子类而变化；非自限定类型的参数类型则不能随子类变化
 */

class Base{
}

class Derived extends Base{}

interface OrdinaryGetter{
    Base get();
}

interface DerivedGetter extends OrdinaryGetter{
    // 允许覆盖父类方法以返回非基类的其它类型
    Derived get();
}

public class CovariantReturnTypes {
    void test(DerivedGetter derivedGetter){
        Derived derived = derivedGetter.get();
    }
}

// -------------------------------------------------------------------------------------

class OrdinarySetter{
    void set(Base base){
        Util.println("OrdinarySetter.set(Base)");
    }
}

class DerivedSetter extends OrdinarySetter{
    void set(Derived derived){
        Util.println("DerivedSetter.set(Derived)");
    }
}

/**
 * 两个 set() 都存在、可用
 */
class OrdinaryArguments{
    public static void main(String[] args){
        Base base = new Base();
        Derived derived = new Derived();
        DerivedSetter setter = new DerivedSetter();
        setter.set(derived);
        setter.set(base);
    }
}

// -------------------------------------------------------------------------------------

/**
 * 自限定类型的导出方法将接受确切类型
 * @param <T>
 */
interface SelfBoundedSetter<T extends SelfBoundedSetter<T>>{
    void set(T arg);
    T get();
}

interface Setter extends SelfBoundedSetter<Setter>{}

class SelfBoundedAndCovariantArguments{
    void testA(Setter set1, Setter set2, SelfBoundedSetter setter){
        set1.set(set2);

        // Error: set(Setter) in SelfBoundedSetter<Setter> cannot be applied to (SelfBoundedSetter)
        // set1.set(setter);
    }
}