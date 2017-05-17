package com.ryan.concurrency;

/**
 * Created by MUFCRyan on 2017/5/17.
 * Book Page675 Chapter 21.3.1 不正确的访问资源
 * 递增不是原子性操作，必须时需要同步处理
 */

public class EvenGenerator extends IntGenerator {
    private int currentEvenValue = 0;
    @Override
    public int next() {
        ++currentEvenValue;
        ++currentEvenValue;
        return currentEvenValue;
    }
    public static void main(String[] args){
        EvenChecker.test(new EvenGenerator());
    }
}
