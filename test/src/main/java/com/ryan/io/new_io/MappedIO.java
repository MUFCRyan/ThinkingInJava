package com.ryan.io.new_io;

import com.ryan.util.Util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.IntBuffer;
import java.nio.channels.FileChannel;

/**
 * !!IMPORTANT
 * Created by MUFCRyan on 2017/5/10.
 * Book Page564 Chapter 18.10.6 内存映射文件：性能，映射文件中的所有输出必须使用 RandomAccessFile
 */

public class MappedIO {
    private static int numOfInts = 4000000;
    private static int numOfUbuffInts = 200000;

    private abstract static class Tester {
        private String name;

        public Tester(String name) {
            this.name = name;
        }

        public abstract void test() throws IOException;

        public void runTest() {
            Util.print(name + ": ");
            try {
                long start = System.nanoTime();
                test();
                long duration = System.nanoTime() - start;
                Util.format("%.2f\n", duration / 1.0e9);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private static Tester[] tests = {
            new Tester("Stream Write") {
                @Override
                public void test() throws IOException {
                    DataOutputStream out = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(new File("temp.tmp"))));
                    for (int i = 0; i < numOfInts; i++) {
                        out.writeInt(i);
                    }
                    out.close();
                }
            },

            new Tester("Mapped Write") {
                @Override
                public void test() throws IOException {
                    FileChannel channel = new RandomAccessFile("temp.tmp", "rw").getChannel();
                    IntBuffer out = channel.map(FileChannel.MapMode.READ_WRITE, 0, channel.size()).asIntBuffer();
                    for (int i = 0; i < numOfInts; i++) {
                        out.put(i);
                    }
                    channel.close();
                }
            },

            new Tester("Stream Read") {
                @Override
                public void test() throws IOException {
                    DataInputStream in = new DataInputStream(new BufferedInputStream(new FileInputStream("temp.tmp")));
                    for (int i = 0; i < numOfInts; i++) {
                        in.readInt();
                    }
                    in.close();
                }
            },

            new Tester("Mapped Read") {
                @Override
                public void test() throws IOException {
                    FileChannel channel = new FileInputStream(new File("temp.tmp")).getChannel();
                    IntBuffer in = channel.map(FileChannel.MapMode.READ_ONLY, 0, channel.size()).asIntBuffer();
                    while (in.hasRemaining())
                        in.get();
                    channel.close();
                }
            },

            new Tester("Stream Read/Write") {
                @Override
                public void test() throws IOException {
                    RandomAccessFile inOut = new RandomAccessFile(new File("temp.tmp"), "rw");
                    inOut.writeInt(1);
                    for (int i = 0; i < numOfUbuffInts; i++) {
                        inOut.seek(inOut.length() - 4);
                        inOut.writeInt(inOut.readInt());
                    }
                    inOut.close();
                }
            },

            new Tester("Mapped Read/Write") {
                @Override
                public void test() throws IOException {
                    FileChannel channel = new RandomAccessFile(new File("temp.tmp"), "rw").getChannel();
                    IntBuffer inOut = channel.map(FileChannel.MapMode.READ_WRITE, 0, channel.size()).asIntBuffer();
                    inOut.put(0);
                    for (int i = 1; i < numOfUbuffInts; i++) {
                        inOut.put(inOut.get(i - 1));
                    }
                    channel.close();
                }
            }
    };

    public static void main(String[] args){
        for (Tester test : tests) {
            test.runTest();
        }
    }
}
