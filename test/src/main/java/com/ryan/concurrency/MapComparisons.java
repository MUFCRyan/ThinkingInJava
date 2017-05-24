package com.ryan.concurrency;

import com.ryan.array.CountingGenerator;
import com.ryan.containers.MapData;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by MUFCRyan on 2017/5/24.
 * Book Page758 Chapter 21.9.2 免锁容器：比较各种 Map 实现
 * 向 ConcurrentHashMap 添加写入者的影响还不如 CopyOnWriteArrayList 明显，因为 ConcurrentHashMap 采用了一种可以明显地最小化写入影响的技术
 */

abstract class MapTest extends Tester<Map<Integer, Integer>> {
    MapTest(String testId, int nReaders, int nWriters) {
        super(testId, nReaders, nWriters);
    }

    class Reader extends TestTask {
        long result = 0;
        @Override
        void test() {
            for (int i = 0; i < testCycles; i++) {
                for (int index = 0; index < containerSize; index++) {
                    result += testContainer.get(index);
                }
            }
        }
        @Override
        void putResults() {
            readResult += result;
            readTime += duration;
        }
    }

    class Writer extends TestTask {
        @Override
        void test() {
            for (int i = 0; i < testCycles; i++) {
                for (int index = 0; index < containerSize; index++) {
                    testContainer.put(index, writeData[index]);
                }
            }
        }
        @Override
        void putResults() {
            writeTime += duration;
        }
    }

    @Override
    void startReadersAndWriters() {
        for (int i = 0; i < nReaders; i++) {
            sExec.execute(new Reader());
        }
        for (int i = 0; i < nWriters; i++) {
            sExec.execute(new Writer());
        }
    }
}

class SynchronizedHashMapTest extends MapTest {
    SynchronizedHashMapTest(int nReaders, int nWriters) {
        super("SynchronizedHashMapTest", nReaders, nWriters);
    }
    @Override
    Map<Integer, Integer> containerInitializer() {
        return Collections.synchronizedMap(new HashMap<>(MapData.map(new CountingGenerator.Integer(), new CountingGenerator.Integer(), containerSize)));
    }
}

class ConcurrentHashMapTest extends MapTest {
    ConcurrentHashMapTest(int nReaders, int nWriters) {
        super("ConcurrentHashMapTest", nReaders, nWriters);
    }
    @Override
    Map<Integer, Integer> containerInitializer() {
        return new ConcurrentHashMap<>(MapData.map(new CountingGenerator.Integer(), new CountingGenerator.Integer(), containerSize));
    }
}

public class MapComparisons {
    public static void main(String[] args){
        Tester.initMain(args);
        new SynchronizedHashMapTest(10, 0);
        new SynchronizedHashMapTest(9, 1);
        new SynchronizedHashMapTest(5, 5);
        new ConcurrentHashMapTest(10, 0);
        new ConcurrentHashMapTest(9, 1);
        new ConcurrentHashMapTest(5, 5);
        Tester.sExec.shutdown();
    }
}
