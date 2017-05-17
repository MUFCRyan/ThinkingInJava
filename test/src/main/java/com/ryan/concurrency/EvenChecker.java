package com.ryan.concurrency;

import com.ryan.util.Util;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by MUFCRyan on 2017/5/17.
 * Book Page675 Chapter 21.3 共享受限资源
 * Book Page675 Chapter 21.3.1 不正确的访问资源
 */

public class EvenChecker implements Runnable {
    private IntGenerator generator;
    private final int id;
    public EvenChecker(IntGenerator generator, int id){
        this.generator = generator;
        this.id = id;
    }
    @Override
    public void run() {
        while (!generator.isCanceled()){
            int value = generator.next();
            if (value % 2 != 0){
                Util.println(value + " not even!");
                generator.cancel();
            }
        }
    }
    // Test any type of IntGenerator
    public static void test(IntGenerator generator, int count){
        Util.println("Press Control-C to exit");
        ExecutorService exec = Executors.newCachedThreadPool();
        for (int i = 0; i < count; i++) {
            exec.execute(new EvenChecker(generator, i));
        }
        exec.shutdown();
    }
    public static void test(IntGenerator generator) {
        test(generator, 10);
    }
}
