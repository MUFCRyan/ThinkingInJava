package com.ryan.concurrency;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

/**
 * Created by MUFCRyan on 2017/5/23.
 * Book Page733 Chapter 21.7.6 Semaphore
 * 对象池：管理数个对象，使用时签出，完毕后签回
 */

public class Pool<T> {
    private int size;
    private List<T> items = new ArrayList<>();
    private volatile boolean[] checkedOut;
    private Semaphore available;
    public Pool(Class<T> classObject, int size){
        this.size = size;
        checkedOut = new boolean[size];
        available = new Semaphore(size, true);
        // Load pool with objects that can be checked out
        for (int i = 0; i < size; i++) {
            try {
                // Assumes a default constructor
                items.add(classObject.newInstance());
            } catch (Exception e){
                throw new RuntimeException(e);
            }
        }
    }
    public T checkOut() throws InterruptedException {
        available.acquire();
        return getItem();
    }
    public void checkIn(T t){
        if (releaseItem(t))
            available.release();
    }
    public synchronized T getItem(){
        for (int i = 0; i < size; i++) {
            if (!checkedOut[i]){
                checkedOut[i] = true;
                return items.get(i);
            }
        }
        return null;
    }
    public synchronized boolean releaseItem(T item){
        int index = items.indexOf(item);
        if (index == -1)
            return false;
        if (checkedOut[index]){
            checkedOut[index] = false;
            return true;
        }
        return false; // Wasn't checked out
    }
}
