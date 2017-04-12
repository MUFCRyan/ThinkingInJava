package com.ryan.generic.border;

import com.ryan.util.Util;

import java.awt.Color;

/**
 * Created by MUFCRyan on 2017/4/6.
 * 通过继承在边界上添加限制
 */

class HolderItem<T> {
    T item;

    HolderItem(T item) {
        this.item = item;
    }

    T getItem() {
        return item;
    }
}

class Colored2<T extends HasColor> extends HolderItem<T> {
    Colored2(T item) {
        super(item);
    }

    Color color() {
        return item.getColor();
    }
}

class ColoredDimension2<T extends Dimension & HasColor> extends Colored2<T> {
    ColoredDimension2(T item) {
        super(item);
    }

    int getX() {
        return item.x;
    }

    int getY() {
        return item.y;
    }

    int getZ() {
        return item.z;
    }
}

class Solid2<T extends Dimension & HasColor & Weight> extends ColoredDimension2<T> {
    Solid2(T item) {
        super(item);
    }

    int weight() {
        return item.weight();
    }
}

public class InheritBounds {
    public static void main(String[] args){
        Solid2<Bounded> solid2 = new Solid2<>(new Bounded());
        Util.println(solid2.color());
        Util.println(solid2.getY());
        Util.println(solid2.weight());
    }
}
