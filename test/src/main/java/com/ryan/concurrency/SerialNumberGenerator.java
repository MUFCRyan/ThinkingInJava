package com.ryan.concurrency;

/**
 * Created by MUFCRyan on 2017/5/17.
 * Book Page683 Chapter 21.3.3 原子性与易变性
 * 如果一个域可能被多个任务同时访问或其中至少有一个是写入任务就应该将其设置为 volatile 的；域定义为 volatile 后就会告诉编译器不要执行任何移除
 *      读取和写入操作的优化，这些操作的目的是用线程中的局部变量维护对这个域的精确同步；实际读取和写入都是直接针对内存的未被缓存；volatile 定义
 *      的域的对其递增的原子性毫无影响
 * */

public class SerialNumberGenerator {
    private static volatile int serialNumber = 0;
    public synchronized static int nextSerialNumber(){
        return serialNumber++; // If not use synchronized then this is not thread-safe
    }
}
