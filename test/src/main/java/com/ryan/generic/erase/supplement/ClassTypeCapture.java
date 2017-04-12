package com.ryan.generic.erase.supplement;

import com.ryan.util.Util;

/**
 * Created by MUFCRyan on 2017/4/6.
 * 类型捕获
 */

class Building{}

class House extends Building{}

public class ClassTypeCapture<T> {
    Class<T> kind;
    public ClassTypeCapture(Class<T> kind){
        this.kind = kind;
    }
    public boolean isInstance(Object obj){
        return kind.isInstance(obj);
    }
    public static void main(String[] args){
        ClassTypeCapture<Building> capture = new ClassTypeCapture<>(Building.class);
        boolean instance1 = capture.isInstance(new Building());
        boolean instance2 = capture.isInstance(new House());
        Util.println("building is instance: " + instance1);
        Util.println("house is instance: " + instance2);
    }
}
