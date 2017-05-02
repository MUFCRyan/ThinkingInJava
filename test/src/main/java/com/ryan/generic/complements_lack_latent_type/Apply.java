package com.ryan.generic.complements_lack_latent_type;

import com.ryan.util.Util;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by MUFCRyan on 2017/5/2.
 * Book Page421 Chapter 15.17.2 方法应用于序列
 */

public class Apply {
    public static <T, S extends Iterable<? extends T>> void apply(S seq, Method method, Object... args){
        try {
            for (T t : seq) {
                method.invoke(t, args);
            }
        } catch (Exception e){
            throw new RuntimeException(e);
        }
    }
}

class Shape{
    public void rotate(){}
    public void resize(int newSize){
        Util.println(this + " resize " + newSize);
    }
}

class Square extends Shape{

}

class FilledList<T> extends ArrayList<T>{
    public FilledList(Class<? extends T> type, int size){
        try {
            for (int i = 0; i < size; i++) {
                add(type.newInstance());

            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

class ApplyTest{
    public static void main(String[] args) throws NoSuchMethodException {
        List<Shape> shapeList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            shapeList.add(new Shape());
        }
        Apply.apply(shapeList, Shape.class.getMethod("rotate"));
        Apply.apply(shapeList, Shape.class.getMethod("resize", int.class), 5);
        Util.println("shapeList ----------------------------------");

        List<Square> squareList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            squareList.add(new Square());
        }
        Apply.apply(squareList, Square.class.getMethod("rotate"));
        Apply.apply(squareList, Square.class.getMethod("resize", int.class), 5);
        Util.println("squareList ----------------------------------");

        try {
            Apply.apply(new FilledList<Shape>(Shape.class, 10), Shape.class.getMethod("rotate"));
            Apply.apply(new FilledList<Square>(Square.class, 10), Shape.class.getMethod("rotate"));
            Util.println("FilledList ----------------------------------");

            SimpleQueue<Shape> shapeQueue = new SimpleQueue<>();
            for (int i = 0; i < 5; i++) {
                shapeQueue.add(new Shape());
                shapeQueue.add(new Square());
            }
            Apply.apply(shapeQueue, Shape.class.getMethod("rotate"));
            Util.println("shapeQueue ----------------------------------");
        } catch (Exception e){
            Util.println(e.getMessage());
        }
    }
}
