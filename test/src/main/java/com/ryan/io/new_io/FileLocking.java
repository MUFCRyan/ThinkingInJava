package com.ryan.io.new_io;

import com.ryan.util.Util;

import java.io.FileOutputStream;
import java.nio.channels.FileLock;
import java.util.concurrent.TimeUnit;

/**
 * Created by MUFCRyan on 2017/5/10.
 * Book Page566 Chapter 18.10.7 文件加锁：lock()、tryLock() 可获得 FileLock，前者阻塞，后者非阻塞，二者的重载方法可对文件的一部分上锁
 * Socket/Datagram/ServerSocketChannel 不需加锁，因它们是从单进程实体继承而来，且通过不再两个进程间共享网络 socket
 * 对独占/共享锁的支持必须由底层的操作系统提供，若 OS 支持共享锁并为每个请求都创建一个锁，就会使用独占锁；锁的类型由 FileLock.isShared() 查询
 * 注：只能获得通道上的锁，不能获得缓冲器上的锁
 */

public class FileLocking {
    public static void main(String[] args) throws Exception {
        FileOutputStream out = new FileOutputStream("file.txt");
        FileLock fileLock = out.getChannel().tryLock();
        if (fileLock != null){
            Util.println("Locked File");
            TimeUnit.MILLISECONDS.sleep(100);
            fileLock.release();
            Util.println("Release Lock");
        }
        out.close();
    }
}
