package com.ryan.io.new_io;

import com.ryan.util.Util;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;

/**
 * Created by MUFCRyan on 2017/5/10.
 * Book Page567 Chapter 18.10.7 对映射文件部分加锁
 */

public class LockingMappingFiles {
    static int length = 0x8FFFFFF; // 128MB
    static FileChannel channel;
    public static void main(String[] args) throws IOException {
        channel = new RandomAccessFile("test.dat", "rw").getChannel();
        MappedByteBuffer map = channel.map(FileChannel.MapMode.READ_WRITE, 0, length);
        for (int i = 0; i < length; i++) {
            map.put((byte) 'x');
        }
        new LockAndModify(map, 0, length / 3);
        new LockAndModify(map, length / 2, length / 2 + length / 4);
    }

    private static class LockAndModify extends Thread{
        private ByteBuffer buffer;
        private int start, end;
        LockAndModify(ByteBuffer buffer, int start, int end){
            this.start = start;
            this.end = end;
            buffer.limit(end);
            buffer.position(start);
            this.buffer = buffer.slice();
            start();
        }

        @Override
        public void run() {
            try {
                // Exclusive lock with no overlap
                FileLock lock = channel.lock(start, end, false);
                Util.println("Lock: " + start + " to " + end);
                // Perform modification
                while (buffer.position() < buffer.limit() - 1)
                    buffer.put((byte) (buffer.get() + 1));
                lock.release();
                Util.println("Released: " + start + " to " + end);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }
    }
}
