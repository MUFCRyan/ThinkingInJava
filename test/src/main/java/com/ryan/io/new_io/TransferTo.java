package com.ryan.io.new_io;

import com.ryan.util.Util;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * Created by MUFCRyan on 2017/5/9.
 * Book Page554 Chapter 18.10 新 I/O
 * 可以使用 transferTo/From 将两个通道相连
 */

public class TransferTo {
    private static final int SIZE = 1024;
    public static void main(String[] args) throws IOException {
        if (args.length == 2){
            Util.println("arguments: source file destfile");
            System.exit(1);
        }
        FileChannel inChannel = new FileInputStream(args[0]).getChannel()
                , outChannel = new FileOutputStream(args[1]).getChannel();
        ByteBuffer buffer = ByteBuffer.allocate(SIZE);
        inChannel.transferTo(0, inChannel.size(), outChannel);
        // Or: outChannel.transferFrom(inChannel, 0, inChannel.size());
    }
}
