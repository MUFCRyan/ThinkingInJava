package com.ryan.generic.complements_lack_latent_type;

/**
 * Created by MUFCRyan on 2017/5/2.
 * Book Page422 Chapter 15.17.2 方法应用于序列
 */
//: generics/SimpleQueue.java
// A different kind of container that is Iterable
import java.util.*;

public class SimpleQueue<T> implements Iterable<T> {
    private LinkedList<T> storage = new LinkedList<T>();
    public void add(T t) { storage.offer(t); }
    public T get() { return storage.poll(); }
    public Iterator<T> iterator() {
        return storage.iterator();
    }
} ///:~
