package com.ryan.enumerated;

import com.ryan.util.Util;

import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.Set;
import java.util.TreeSet;

/**
 * Created by MUFCRyan on 2017/5/11.
 * Book Page594 Chapter 19.4 values() 的特别之处：是由编译器添加的 static 方法，同时编译器会添加一个单个参数的 valueOf()；编译器会将
 * Explore 标记为 final --> 不能继承自 enum
 */

enum Explore{
    HERE, THERE
}

public class Reflection {
    public static Set<String> analyze(Class<?> enumClass){
        Util.println("----- Analyzing " + enumClass + "-----");
        Util.print("Interfaces: ");
        for (Type type : enumClass.getGenericInterfaces()) {
            Util.println(type);
        }
        Util.println("Base: " + enumClass.getSuperclass());
        Util.println("Methods: ");
        Set<String> methods = new TreeSet<>();
        for (Method method : enumClass.getMethods()) {
            methods.add(method.getName());
        }
        Util.println(methods);
        return methods;
    }

    public static void main(String[] args){
        Set<String> exploreMethods = analyze(Explore.class);
        Set<String> enumMethods = analyze(Enum.class);
        Util.println("Explore.containsAll(Enum)? " + exploreMethods.containsAll(enumMethods));
        Util.println("Explore.removeAll(Enum): ");
        exploreMethods.removeAll(enumMethods);
        Util.println(exploreMethods);
        // OSExecute.command("javap Explore");
    }
}

/**
 * 除了 values() 之外还可以用 getEnumConstants() 获取所有 enum 对象实例，该方法位于 Class 类中，所以即便非 enum 中也可以调用，只不过会导致
 * 空指针异常
 */
class NonEnum{
    public static void main(String[] args){
        Class<Integer> integer = Integer.class;
        try {
            Integer[] enumConstants = integer.getEnumConstants();
            for (Integer enumConstant : enumConstants) {
                Util.println(enumConstant);
            }
        } catch (Exception e){
            Util.println(e);
        }
    }
}
