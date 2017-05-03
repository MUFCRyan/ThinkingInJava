package com.ryan.array;

/**
 * Created by MUFCRyan on 2017/5/3.
 * Book Page440 Chapter 16.5 数组与泛型
 * 通常数组与泛型不能很好地结合，不能实例化具有参数化类型的数组 --> 擦除会移除参数类型信息，而数据必须知道其持有的确切类型，以强制保证类型安全，
 *      例如：Peel<Banana>[] peels = new Peel<Banana>[10]; //!Illegal
 * 不过可以参数化数组本身的类型
 */

class ClassParameter<T>{
    public T[] f(T[] arg){
        return arg;
    }
}

class MethodParameter{
    public static <T> T[] f(T[] arg){
        return arg;
    }
}

/**
 * 使用参数化方法的便利之处在于：不必为需要应用的每种不同的类型都使用一个参数去实例化该类，且可以将其定义为静态的
 */
public class ParameterizedArrayType {
    public static void main(String[] args){
        Integer[] ints = {1, 2, 3, 4, 5};
        Double[] doubles = {1.1, 2.2, 3.3, 4.4, 5.5};
        Integer[] ints2 = new ClassParameter<Integer>().f(ints);
        Double[] doubles2 = new ClassParameter<Double>().f(doubles);
        ints2 = MethodParameter.f(ints);
        doubles2 = MethodParameter.f(doubles);
    }
}
