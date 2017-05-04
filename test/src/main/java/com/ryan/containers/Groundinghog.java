package com.ryan.containers;

/**
 * Created by MUFCRyan on 2017/5/4.
 * Book Page488 Chapter 17.9 散列和散列码
 * 需要同时复写 hashCode() 和 equals()
 */

public class Groundinghog {
    private int number;

    public Groundinghog(int number) {
        this.number = number;
    }

    @Override
    public String toString() {
        return "Groundinghog#" + number;
    }

    @Override
    public int hashCode() {
        return number;
    }

    @Override
    public boolean equals(Object o) {
        // instanceof 默认检查了 o 是否为 null，若是 null 返回 false
        return o instanceof Groundinghog && (number == ((Groundinghog) o).number);
    }
}
