package com.ryan.generic.wildcard;

/**
 * Created by MUFCRyan on 2017/4/12.
 *
 */

public class Wildcards {
    // Raw argument:
    static void rawArgs(Holder holder, Object arg){
        holder.set(arg);
        holder.set(new Wildcards());

        Object o = holder.get();
    }

    // Similar to rawArgs(), but errors instead of warnings
    static void unboundedArg(Holder<?> holder, Object arg){
        // cannot set any type object
        // holder.set(arg);
        // holder.set(new Wildcards());

        Object o = holder.get();
    }

    static <T> T exact1(Holder<T> holder){
        T t = holder.get();
        return t;
    }

    static <T> T exact2(Holder<T> holder, T arg){
        holder.set(arg);
        T t = holder.get();
        return t;
    }

    static <T> T wildSubType(Holder<? extends T> holder, T arg){
        // holder.set(arg);
        T t = holder.get();
        return t;
    }

    static <T> void withSubType(Holder<? super T> holder, T arg){
        holder.set(arg);
        //T t = holder.get(); // T 非具体类型，所以会统一向上转型为Object
        Object object = holder.get();
    }

    public static void main(String[] args){
        Holder raw = new Holder<Long>();
        Holder<Long> qualified = new Holder<>();
        Holder<?> unbounded = new Holder<Long>();
        Holder<? extends Long> bounded = new Holder<Long>();
        Long lng = 1l;

        rawArgs(raw, lng);
        rawArgs(qualified, lng);
        rawArgs(unbounded, lng);
        rawArgs(bounded, lng);

        unboundedArg(raw, lng);
        unboundedArg(qualified, lng);
        unboundedArg(unbounded, lng);
        unboundedArg(bounded, lng);

        Object o = exact1(raw);
        // Long aLong = exact1(qualified); //ClassCastException: com.ryan.generic.wildcard.Wildcards cannot be cast to java.lang.Long
        Object o1 = exact1(unbounded);
        //Long aLong1 = exact1(bounded); // ClassCastException: com.ryan.generic.wildcard.Wildcards cannot be cast to java.lang.Long

        exact2(raw, lng);
        exact2(qualified, lng);
        //exact2(unbounded, lng);
        //exact2(bounded, lng);

        Long aLong2 = wildSubType(raw, lng);
        Long aLong3 = wildSubType(qualified, lng);
        Object o2 = wildSubType(unbounded, lng);
        // Long aLong4 = wildSubType(bounded, lng); ClassCastException: com.ryan.generic.wildcard.Wildcards cannot be cast to java.lang.Long

        wildSubType(raw, lng);
        wildSubType(qualified, lng);
        wildSubType(unbounded, lng);
        wildSubType(bounded, lng);
    }
}
