package com.ryan.generic.wildcard;

import com.ryan.util.Util;

/**
 * !!IMPORTANT
 * Created by MUFCRyan on 2017/4/12.
 * 捕获转换：未指定的通配符类型被捕获，并被转换为确切类型；向一个使用<?>的方法传递原生类型，对于编译器来说可能会推断出实际的类型参数，使得该方法可以回转并调用另一个使用该确切类型的方法
 */

public class CaptureConversion {
    /**
     * 参数在传入f2()<?>中经过再次传入<T>后就能确定其具体类型，不能在f2()中返回T，因为T对于f2()来说是未知的；在f1()内部必须使用过具体类型
     * @param holder
     * @param <T>
     */
    static <T> void f1(Holder<T> holder) {
        T t = holder.get();
        Util.println(t.getClass().getSimpleName());
    }

    static void f2(Holder<?> holder) {
        f1(holder); // Call with captured type
    }

    public static void main(String[] args) {
        Holder raw = new Holder<Integer>(1);
        f1(raw);
        f2(raw);
        Holder rawBasic = new Holder();
        rawBasic.set(new Object());
        f2(rawBasic);
        Holder<?> wildcard = new Holder<Double>(1.0);
        f2(wildcard);
    }
}
