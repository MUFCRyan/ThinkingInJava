package com.ryan.generic.problem;

/**
 * Created by MUFCRyan on 2017/4/26.
 * Book Page404 Chapter 15.11.5 基类劫持接口
 * Comparable<ComparablePet> 接口确定了泛型化参数 ComparablePet，那么 ComparablePet
 * 类及其子类也只能在接口中使用 ComparablePet 而不能使用其它任何类
 */

public class ComparablePet implements Comparable<ComparablePet>{
    @Override
    public int compareTo(ComparablePet comparablePet) {
        return 0;
    }
}

/**
 *  ComparablePet 实现了 Comparable<ComparablePet> 导致参数化类型被 ComparablePet 劫持，任何其他类包括其子类都不能再使用
 */
class Cat extends ComparablePet /*implements Comparable<Cat>*/{
    @Override
    public int compareTo(ComparablePet comparablePet) {
        return super.compareTo(comparablePet);
    }
}

/**
 * 精确的实现父类的相同接口（包括参数类型）可以运行，但是接口方法只是覆盖父类的方法而已
 */
class Hamster extends ComparablePet implements Comparable<ComparablePet>{
    @Override
    public int compareTo(ComparablePet comparablePet) {
        return super.compareTo(comparablePet);
    }
}
