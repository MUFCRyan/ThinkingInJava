package com.ryan.concurrency;

/**
 * Created by MUFCRyan on 2017/5/17.
 * Book Page674 Chapter 21.3 共享受限资源
 */

public abstract class IntGenerator {
    private volatile boolean canceled = false;
    public abstract int next();
    // Allows this to be canceled
    public void cancel(){
        canceled = true;
    }
    public boolean isCanceled(){
        return canceled;
    }
}
