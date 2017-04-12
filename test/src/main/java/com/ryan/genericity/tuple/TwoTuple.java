package com.ryan.genericity.tuple;

public class TwoTuple<A, B> {
    public final A first;
    public final B second;

    public TwoTuple(A a, B b) {
        first = a;
        second = b;
    }

    public String toString() {
        return "(" + first + ", " + second + ")";
    }
}

class ThreeTuple<A, B, C> extends TwoTuple<A, B> {
    public final C third;

    public ThreeTuple(A a, B b, C c) {
        super(a, b);
        third = c;
    }

    public String toString() {
        return "(" + first + ", " + second + ", " + third + ")";
    }
}

class FourTuple<A, B, C, D> extends ThreeTuple<A, B, C> {
    public final D four;

    public FourTuple(A a, B b, C c, D d) {
        super(a, b, c);
        four = d;
    }

    public String toString() {
        return "(" + first + ", " + second + ", " + third + ", " + four + ")";
    }
}

class FiveTuple<A, B, C, D, E> extends FourTuple<A, B, C, D> {
    public final E five;

    public FiveTuple(A a, B b, C c, D d, E e) {
        super(a, b, c, d);
        five = e;
    }

    public String toString() {
        return "(" + first + ", " + second + ", " + third + ", " + four + ", " + five + ")";
    }
}

class SixTuple<A, B, C, D, E, F> extends FiveTuple<A, B, C, D, E> {
    public final F six;

    public SixTuple(A a, B b, C c, D d, E e, F f) {
        super(a, b, c, d, e);
        six = f;
    }

    public String toString() {
        return "(" + first + ", " + second + ", " + third + ", " + four + ", " + five + ", " + six + ")";
    }
}