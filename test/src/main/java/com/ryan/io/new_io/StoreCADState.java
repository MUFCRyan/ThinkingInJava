package com.ryan.io.new_io;

import com.ryan.util.Util;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by MUFCRyan on 2017/5/11.
 * Book Page583 Chapter 18.12.3 使用“持久性”
 * static 值必须手动实现，步骤：
 *      1. 为基类 Shape 添加单独序列化 static 字段的方法
 *      2. 添加对序列化/反序列化方法的调用
 */

abstract class Shape implements Serializable {
    public static final int RED = 1, BLUE = 2, GREEN = 3;
    private int xPos, yPos, dimension;
    private static Random random = new Random(47);
    private static int counter = 0;
    public abstract void setColor(int color);
    public abstract int getColor();
    public Shape(int xVal, int yVal, int dim){
        xPos = xVal;
        yPos = yVal;
        dimension = dim;
    }

    @Override
    public String toString() {
        return getClass() + "color[" + getColor() + "] xPos[" + xPos + "] yPos[" + yPos + "] " + "dim[" + dimension + "]\n";
    }
    public static Shape randomFactory(){
        int xVal = random.nextInt(100);
        int yVal = random.nextInt(100);
        int dim = random.nextInt(100);
        switch (counter ++ % 3){
            default:
            case 0:
                return new Circle(xVal, yVal, dim);
            case 1:
                return new Square(xVal, yVal, dim);
            case 2:
                return new Line(xVal, yVal, dim);
        }
    }
}

class Circle extends Shape{
    public static int color = RED;
    public Circle(int xVal, int yVal, int dim) {
        super(xVal, yVal, dim);
    }

    public static void serializeStaticState(ObjectOutputStream stream) throws IOException{
        stream.writeInt(color);
    }

    public static void deserializeStaticState(ObjectInputStream stream) throws IOException{
        color = stream.readInt();
    }

    @Override
    public void setColor(int color) {
        this.color = color;
    }

    @Override
    public int getColor() {
        return color;
    }
}

class Square extends Shape{
    public static int color = BLUE;
    public Square(int xVal, int yVal, int dim) {
        super(xVal, yVal, dim);
        color = RED;
    }

    public static void serializeStaticState(ObjectOutputStream stream) throws IOException{
        stream.writeInt(color);
    }

    public static void deserializeStaticState(ObjectInputStream stream) throws IOException{
        color = stream.readInt();
    }

    @Override
    public void setColor(int color) {
        this.color = color;
    }

    @Override
    public int getColor() {
        return color;
    }
}

class Line extends Shape{
    public static int color = GREEN;
    public Line(int xVal, int yVal, int dim) {
        super(xVal, yVal, dim);
    }

    public static void serializeStaticState(ObjectOutputStream stream) throws IOException{
        stream.writeInt(color);
    }

    public static void deserializeStaticState(ObjectInputStream stream) throws IOException{
        color = stream.readInt();
    }

    @Override
    public void setColor(int color) {
        this.color = color;
    }

    @Override
    public int getColor() {
        return color;
    }
}

public class StoreCADState {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        List<Class<? extends Shape>> shapeTypes = new ArrayList<>();
        shapeTypes.add(Circle.class);
        shapeTypes.add(Square.class);
        shapeTypes.add(Line.class);

        List<Shape> shapes = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            shapes.add(Shape.randomFactory());
        }
        for (int i = 0; i < shapes.size(); i++) {
            shapes.get(i).setColor(Shape.RED);
        }
        ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("CADState.out"));
        out.writeObject(shapeTypes);
        Circle.serializeStaticState(out);
        Square.serializeStaticState(out);
        Line.serializeStaticState(out);
        out.writeObject(shapes);
        Util.println(shapes);
        out.close();
    }
}

/**
 * static 的 color 无法自动有效序列化，需要手动实现
 */
class RecoverCADState {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        ObjectInputStream in = new ObjectInputStream(new FileInputStream("CADState.out"));
        List<Class<? extends Shape>> shapeTypes = (List<Class<? extends Shape>>) in.readObject();
        Circle.deserializeStaticState(in);
        Square.deserializeStaticState(in);
        Line.deserializeStaticState(in);
        List<Shape> shapes = (List<Shape>) in.readObject();
        Util.println(shapes);
    }
}
