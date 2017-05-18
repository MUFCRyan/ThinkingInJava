package com.ryan.concurrency;

import com.ryan.util.Util;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousCloseException;
import java.nio.channels.ClosedByInterruptException;
import java.nio.channels.SocketChannel;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * Created by MUFCRyan on 2017/5/18.
 * Book Page698 Chapter 21.4.3 中断：nio 类库中的类可以自动地响应中断
 */

class NIOBlocked implements Runnable {
    private final SocketChannel channel;
    public NIOBlocked(SocketChannel channel){
        this.channel = channel;
    }
    @Override
    public void run() {
        try {
            Util.println("Waiting for read() in " + this);
            channel.read(ByteBuffer.allocate(1));
        } catch (ClosedByInterruptException e){
            Util.println("ClosedByInterruptException");
        } catch (AsynchronousCloseException e){
            Util.println("AsynchronousCloseException");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Util.println("Exiting NIOBlocked.run() " + this);
    }
}

public class NIOInterruption {
    public static void main(String[] args) throws IOException, InterruptedException {
        ExecutorService exec = Executors.newCachedThreadPool();
        ServerSocket se = new ServerSocket(59544);
        InetSocketAddress socketAddress = new InetSocketAddress("localhost", 59544);
        SocketChannel channel1 = SocketChannel.open(socketAddress);
        SocketChannel channel2 = SocketChannel.open(socketAddress);
        Future<?> future = exec.submit(new NIOBlocked(channel1)); // future 只有在将中断发送给一个线程且不发送给其它线程时才是必需的
        exec.execute(new NIOBlocked(channel2));
        exec.shutdown();
        TimeUnit.SECONDS.sleep(1);
        // Produce an interrupt via channel
        future.cancel(true);
        TimeUnit.SECONDS.sleep(1);
        // Release the block by closing the channel
        channel2.close();
    }
}
