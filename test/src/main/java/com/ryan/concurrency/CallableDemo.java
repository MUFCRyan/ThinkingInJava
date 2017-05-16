package com.ryan.concurrency;

import com.ryan.util.Util;

import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Created by MUFCRyan on 2017/5/16.
 * Book Page658 Chapter 21.2.4 从任务中产生返回值
 * Runnable 不返回任何值，Callable 会产生返回值
 */

class TashWithResult implements Callable<String> {
    private int id;
    public TashWithResult(int id){
        this.id = id;
    }

    @Override
    public String call() throws Exception {
        return "result of TaskWithResult " + id;
    }
}

public class CallableDemo {
    public static void main(String[] args){
        ExecutorService exec = Executors.newCachedThreadPool();
        ArrayList<Future<String>> results = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            results.add(exec.submit(new TashWithResult(i)));
        }
        for (Future<String> result : results) {
            try {
                // get() blocks until completion
                Util.println(result.get());
            } catch (InterruptedException e) {
                Util.println(e);
                return;
            } catch (ExecutionException e) {
                Util.println(e);
            }finally {
                exec.shutdown();
            }
        }
    }
}

