package com.ryan.generic.complements_lack_latent_type;

import com.ryan.generic.coffee.Coffee;
import com.ryan.generic.coffee.Latte;
import com.ryan.generic.coffee.Mocha;
import com.ryan.generic.generator.Generator;
import com.ryan.util.Util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by MUFCRyan on 2017/5/2.
 * Book Page424 Chapter 15.17.4 适配器仿真潜在类型机制
 * 潜在类型机制实际上创建了一个包含了所需方法的“隐式接口”，遵循规则：若手工编写了必需的接口，它就应该能解决问题；
 * 从我们拥有的接口中编写代码以产生我们需要的接口是适配器模式的一种典型使用；可以使用适配器来适配已有的接口 --> 产生我们想要的接口
 */

interface Addable<T> {
    void add(T t);
}

/**
 * 这种方式是对缺乏潜在类型机制的补偿，允许编写出真正的泛型化代码（价值所在）；但是有额外的步骤且类库的创建者和消费者必须都能理解
 */
public class Fill {
    public static <T> void fill(Addable<T> addable, Class<? extends T> classToken, int size) {
        try {
            for (int i = 0; i < size; i++)
                addable.add(classToken.newInstance()); // 类实例添加
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> void fill(Addable<T> addable, Generator<? extends T> generator, int size){
        try {
            for (int i = 0; i < size; i++) {
                addable.add(generator.next()); // Generator 添加
            }
        } catch (Exception e){

        }
    }
}

class AddableCollectionAdapter<T> implements Addable<T>{
    private Collection<T> mCollection;
    public AddableCollectionAdapter(Collection<T> collection){
        mCollection = collection;
    }

    @Override
    public void add(T t) {
        mCollection.add(t);
    }
}

/**
 * 获取对象更加优雅的方法，不必显式的写出来
 */
class Adapter{
    public static <T> Addable<T> collectionAdapter(Collection<T> collection){
        return new AddableCollectionAdapter<>(collection);
    }
}

class AddableSimpleQueue<T> extends SimpleQueue<T> implements Addable<T>{
    public void add(T item){
        super.add(item);
    }
}

class FillTest{
    public static void main(String[] args){
        List<Coffee> coffeeList = new ArrayList<>();
        Fill.fill(new AddableCollectionAdapter<>(coffeeList), Coffee.class, 5);
        // Helper method captures the type
        Fill.fill(new AddableCollectionAdapter<>(coffeeList), Latte.class, 4);

        for (Coffee coffee : coffeeList) {
            Util.println(coffee);
        }

        Util.println("---------------------------------------------------------");

        AddableSimpleQueue<Coffee> coffeeQueue = new AddableSimpleQueue<>();
        Fill.fill(coffeeQueue, Mocha.class, 3);
        Fill.fill(coffeeQueue, Latte.class, 2);
        for (Coffee coffee : coffeeQueue) {
            Util.println(coffee);
        }
    }
}


