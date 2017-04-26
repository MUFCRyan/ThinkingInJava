package com.ryan.generic.self_bounded;

import com.ryan.util.Util;

/**
 * !!IMPORTANT
 * Created by MUFCRyan on 2017/4/26.
 * Book Page404 Chapter 15.12 自限定类型
 * 惯用法：class SelfBounded<T extends SelfBounded<T>> { // ...，强调了 extends 关键字在用于边界和用于创建子类明显不同
 *
 * Book Page404 Chapter 15.12.1 古怪的循环（CRG）泛型：类很古怪的出现在其基类当中；
 * 本质：基类使用导出类替代其参数 --> 所产生的类中将使用确切了类型而非基类型
 */

class GenericType<T> {}

public class CuriousRecurringGeneric extends GenericType<CuriousRecurringGeneric>{

}

class BasicHolder<T> {
    T element;
    void set(T arg){
        element = arg;
    }
    T get(){
        return element;
    }
    void f(){
        Util.println(element.getClass().getSimpleName());
    }
}

class SubType extends BasicHolder<SubType>{}

class CRGWithBasicHolder{
    public static void main(String[] args){
        SubType subType1 = new SubType(), subType2 = new SubType();
        subType1.set(subType2);
        SubType subType = subType1.get(); // 返回值会直接使用确切类型 SubType 而非基类 BasicHolder
        subType1.f();
    }
}