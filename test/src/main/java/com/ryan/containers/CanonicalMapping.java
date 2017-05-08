package com.ryan.containers;

import com.ryan.util.Util;

import java.util.WeakHashMap;

/**
 * Created by MUFCRyan on 2017/5/8.
 * Book Page519 Chapter 17.12.1 WeakHashMap：用于保存WeakReference -->
 *      使得规范映射更易使用，在这种映射中，每个值只保存一份实例供读取使用以节省空间；映射可将值作为其初始化中的一部分，通常在需要时生成；
 *      允许垃圾回收器自动清理键值（触发条件：不再使用该键）、对于向其中添加的键值无要求，添加时自动包装一层 WeakReference
 */

class Element{
    private String ident;
    public Element(String id){
        ident = id;
    }

    @Override
    public String toString() {
        return ident;
    }

    @Override
    public int hashCode() {
        return ident.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof Element && ident.equals(((Element)o).ident);
    }

    @Override
    protected void finalize() throws Throwable {
        Util.println("Finalizing " + getClass().getSimpleName() + " " + ident);
    }
}

class Key extends Element{

    public Key(String id) {
        super(id);
    }
}

class Value extends Element{

    public Value(String id) {
        super(id);
    }
}

public class CanonicalMapping {
    public static void main(String[] args){
        int size = 1000;
        // Or choose size via the command line
        if (args.length > 0)
            size = new Integer(args[0]);
        Key[] keys = new Key[size];
        WeakHashMap<Key, Value> map = new WeakHashMap<>();
        for (int i = 0; i < size; i++) {
            Key key = new Key(i + "");
            Value value = new Value(i + "");
            if (i % 3 == 0)
                keys[i] = key; // Save as "real" references
            map.put(key, value);
        }
        System.gc();
    }
}
