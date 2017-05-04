package com.ryan.containers;

import com.ryan.util.Util;

import java.util.LinkedList;

/**
 * Created by MUFCRyan on 2017/5/4.
 * Book Page482 Chapter 17.7.2 双向队列
 */

public class Deque<T> {
    private LinkedList<T> deque = new LinkedList<T>();
    public void addFirst(T item){
        deque.addFirst(item);
    }
    public void addLast(T item){
        deque.addLast(item);
    }
    public T getFirst(){
        return deque.getFirst();
    }
    public T getLast(){
        return deque.getLast();
    }
    public T removeFirst(){
        return deque.removeFirst();
    }
    public T removeLast(){
        return deque.removeLast();
    }
    public int size(){
        return deque.size();
    }

    @Override
    public String toString() {
        return deque.toString();
    }
}

class DequeTest{
    static void fillTest(Deque<Integer> deque){
        for (int i = 20; i < 27; i++) {
            deque.addFirst(i);
        }
        for (int i = 50; i < 55; i++) {
            deque.addLast(i);
        }
    }

    public static void main(String[] args){
        Deque<Integer> deque = new Deque<>();
        fillTest(deque);
        Util.println(deque);
        while (deque.size() != 0){
            Util.print(deque.removeFirst() + " ");
        }
        Util.println();
        fillTest(deque);
        while (deque.size() != 0){
            Util.print(deque.removeLast() + " ");
        }
    }
}
