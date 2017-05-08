package com.ryan.containers;

import com.ryan.util.Util;

import java.lang.ref.PhantomReference;
import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;
import java.util.LinkedList;

/**
 * Created by MUFCRyan on 2017/5/8.
 * Book Page518 Chapter 17.12 持有引用
 * SoftReference 用以实现内存敏感的告诉缓存
 * WeakReference 用以实现“规范映射”（canonicalizing mappings），不妨碍垃圾回收器回收映射的“键”/“值”，其中的对象实例可以在程序的多处被使用 -->
 *      以节省存储空间
*  PhantomReference 用以调度回收前的清理工作，比 Java机制更加灵活
 * Soft/WeakReference 可以选择是否放入 ReferenceQueue（“用作回收前清理工作”的工具），PhantomReference 只能依赖于
 *      ReferenceQueue；ReferenceQueue 总是生成一个包含 null 对象的 Reference 的 Reference，若要利用此机制 --> 可继承特定的
 *      Reference 类，然后为该类添加必要的新方法
 */

class VeryBig{
    private static final int SIZE = 10000;
    private long[] la = new long[10000];
    private String ident;
    public VeryBig(String id){
        ident = id;
    }

    @Override
    public String toString() {
        return ident;
    }

    @Override
    protected void finalize() throws Throwable {
        Util.println("Finalizing " + ident);
    }
}

public class References {
    private static ReferenceQueue<VeryBig> referenceQueue = new ReferenceQueue<>();
    private static void checkQueue(){
        Reference<? extends VeryBig> inq = referenceQueue.poll();
        if (inq != null)
            Util.println("In queue: " + inq.get());
    }

    public static void main(String[] args){
        int size = 10;
        // Or choose size via the command line
        if (args.length > 0)
            size = new Integer(args[0]);
        LinkedList<SoftReference<VeryBig>> sa = new LinkedList<>();
        for (int i = 0; i < size; i++) {
            sa.add(new SoftReference<>(new VeryBig("Soft " + i), referenceQueue));
            Util.println("Just created: " + sa.getLast());
            checkQueue();
        }

        LinkedList<WeakReference<VeryBig>> wa = new LinkedList<>();
        for (int i = 0; i < size; i++) {
            wa.add(new WeakReference<>(new VeryBig("Weak " + i), referenceQueue));
            Util.println("Just created: " + wa.getLast());
            checkQueue();
        }

        SoftReference<VeryBig> s = new SoftReference<>(new VeryBig("Soft"));
        WeakReference<VeryBig> w = new WeakReference<>(new VeryBig("Weak"));

        System.gc();

        LinkedList<PhantomReference<VeryBig>> pa = new LinkedList<>();
        for (int i = 0; i < size; i++) {
            pa.add(new PhantomReference<>(new VeryBig("Phantom " + i), referenceQueue));
            Util.println("Just Created: " + pa.getLast());
            checkQueue();
        }
    }
}
