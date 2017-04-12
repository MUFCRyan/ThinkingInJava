package com.ryan.generic.wildcard;

/**
 * Created by MUFCRyan on 2017/4/12.
 * 使用确切类型代替通配符的好处是可以用泛型参数来做更多的事，使用通配符必须接受范围更广的参数化类型作为参数
 */

public class Wildcards {
    /**
     * Raw argument:
     * 编译器知道Holder是一个泛型类型，即使次数被标识为原生类型，编译器仍旧知道向set()传递一个Object是不安全的；无论何时只要使用了原生类型都会放弃编译器检查；
     * 调用get()时只能返回一个Object对象，没有任何T类型的对象
     */
    static void rawArgs(Holder holder, Object arg) {
        holder.set(arg);
        holder.set(new Wildcards());

        Object o = holder.get();
    }

    /**
     * Similar to rawArgs(), but errors instead of warnings
     * 原生Holder将持有任何类型的组合，而Holder<?>将持有某种具体类型的同构集合，因此不能只是向其中传递Object
     */
    static void unboundedArg(Holder<?> holder, Object arg) {
        // cannot set any type object
        // holder.set(arg);
        // holder.set(new Wildcards());

        Object o = holder.get();
    }

    /**
     * exact1()和exact2()中使用了确切的泛型参数，二者因有额外的参数导致具有不同的限制
     */
    static <T> T exact1(Holder<T> holder) {
        T t = holder.get();
        return t;
    }

    static <T> T exact2(Holder<T> holder, T arg) {
        holder.set(arg);
        T t = holder.get();
        return t;
    }

    /**
     * Holder类型的上限制被放松为包括持有任何扩展至T的对象的Holder，可以知道任何来自Holder<? extends Fruit>的对象至少是Fruit，
     * Fruit fruit = get()合法
     */
    static <T> T wildSubType(Holder<? extends T> holder, T arg) {
        // holder.set(arg);
        T t = holder.get();
        return t;
    }

    /**
     * 展示了超类型通配符，展示了与wildSubType相反的行为：holder是可以持有任何T的基类型的容器，因此，set()
     * 可以接受T，因为任何工作于基类的对象都可以多态的作用于导出类（此处是T）；但是get()无效，因为有holder持有的类型可以是任何超类型，故唯一安全的就是Object
     */
    static <T> void withSuperType(Holder<? super T> holder, T arg) {
        holder.set(arg);
        //T t = holder.get(); // T 非具体类型，所以会统一向上转型为Object
        Object object = holder.get();
    }

    public static void main(String[] args) {
        Holder raw = new Holder<Long>();
        Holder<Long> qualified = new Holder<>();
        Holder<?> unbounded = new Holder<Long>();
        Holder<? extends Long> bounded = new Holder<Long>();
        Long lng = 1l;

        // 对于迁移兼容性：rawArgs()、unboundedArg()将接受任何Holder的变体而不产生警告
        rawArgs(raw, lng);
        rawArgs(qualified, lng);
        rawArgs(unbounded, lng);
        rawArgs(bounded, lng);

        unboundedArg(raw, lng);
        unboundedArg(qualified, lng);
        unboundedArg(unbounded, lng);
        unboundedArg(bounded, lng);

        Object o = exact1(raw);
        // Long aLong = exact1(qualified); // ClassCastException: com.ryan.generic.wildcard.Wildcards cannot be cast to java.lang.Long
        Object o1 = exact1(unbounded);
        //Long aLong1 = exact1(bounded); // ClassCastException: com.ryan.generic.wildcard.Wildcards cannot be cast to java.lang.Long

        exact2(raw, lng);
        exact2(qualified, lng);
        //exact2(unbounded, lng);
        //exact2(bounded, lng);

        Long aLong2 = wildSubType(raw, lng);
        Long aLong3 = wildSubType(qualified, lng);
        Object o2 = wildSubType(unbounded, lng);
        // Long aLong4 = wildSubType(bounded, lng); //ClassCastException: com.ryan.generic.wildcard.Wildcards cannot be cast to java.lang.Long

        withSuperType(raw, lng);
        withSuperType(qualified, lng);
        // withSuperType(unbounded, lng); // Error: wildSuperType(Holder<? super T>, T) cannot be applied to (Holder<capture of ?>, Long)
        // withSuperType(bounded, lng); // Error: wildSuperType(Holder<? super T>, T) cannot be applied to (Holder<capture of extends Long>, Long)
    }
}
