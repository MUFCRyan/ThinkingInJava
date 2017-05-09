package com.ryan.io.new_io;

import com.ryan.util.Util;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * Created by MUFCRyan on 2017/5/9.
 * Book Page553 Chapter 18.10 新 I/O
 */

public class ChannelCopy {
    private static final int SIZE = 1024;
    public static void main(String[] args) throws IOException {
        if (args.length == 2){
            Util.println("arguments: source file destfile");
            System.exit(1);
        }
        FileChannel inChannel = new FileInputStream(args[0]).getChannel()
                , outChannel = new FileOutputStream(args[1]).getChannel();
        ByteBuffer buffer = ByteBuffer.allocate(SIZE);
        while (inChannel.read(buffer) != -1){
            buffer.flip(); // Prepare for writing
            outChannel.write(buffer);
            buffer.clear(); // Prepare for reading，不清理会导致重复数据
        }
    }
}
