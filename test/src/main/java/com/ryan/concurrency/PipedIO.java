package com.ryan.concurrency;

import com.ryan.util.Util;

import java.io.IOException;
import java.io.PipedReader;
import java.io.PipedWriter;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by MUFCRyan on 2017/5/21.
 * Book Page717 Chapter 21.5.5 任务间使用管道进行 I/O
 * read() 时若没有更多数据管道将自动阻塞，跨平台性差可能出现问题（BlockingQueue 更健壮），相比普通 I/O 其可中断
 */

class Sender implements Runnable {
    private Random mRandom = new Random(47);
    private PipedWriter out = new PipedWriter();
    public PipedWriter getPipedWriter(){
        return out;
    }
    @Override
    public void run() {
        try{
            while (true){
                for (char c = 'A'; c <= 'z'; c++){
                    out.write(c);
                    TimeUnit.MILLISECONDS.sleep(500);
                }
            }
        } catch (IOException e){
            Util.println("Sender write exception: " + e);
        } catch (InterruptedException e) {
            Util.println("Sender sleep interrupted: " + e);
        }
    }
}

class Receiver implements Runnable {
    private PipedReader in;
    public Receiver(Sender sender) throws IOException {
        in = new PipedReader(sender.getPipedWriter());
    }
    @Override
    public void run() {
        try{
            while (true){
                // Blocks until characters are there
                Util.print("Read: " + (char)in.read() + ", ");
            }
        } catch (IOException e){
            Util.println("Receiver read exception: " + e);
        }
    }
}

public class PipedIO {
    public static void main(String[] args) throws IOException, InterruptedException {
        Sender sender = new Sender();
        Receiver receiver = new Receiver(sender);
        ExecutorService exec = Executors.newCachedThreadPool();
        exec.execute(sender);
        exec.execute(receiver);
        TimeUnit.SECONDS.sleep(4);
        exec.shutdownNow();
    }
}
