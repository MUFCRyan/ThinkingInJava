package com.ryan.io.new_io;

import com.ryan.util.Util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Random;

/**
 * Created by MUFCRyan on 2017/5/11.
 * Book Page573 Chapter 18.12 对象序列化：必须显式的进行序列化和反序列化
 * 对 Serializable 对象在反序列化时完全以其存储的二进制位进行还原、不会调用任何构造器
 */

class Data implements Serializable{
    private int num;
    public Data(int num){
        this.num = num;
    }

    @Override
    public String toString() {
        return Integer.toString(num);
    }
}

public class Worm implements Serializable{
    private static Random random = new Random(47);
    private Data[] data = new Data[]{
        new Data(random.nextInt(10)),
        new Data(random.nextInt(10)),
        new Data(random.nextInt(10))
    };
    private Worm next;
    private char c;
    // Value of i == number of segments
    public Worm(int i, char c){
        Util.println("Constructor: " + i);
        this.c = c;
        if (--i > 0)
            next = new Worm(i, (char) (c + 1));
    }
    public Worm(){
        Util.println("Default Constructor");
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder(":");
        builder.append(c);
        builder.append("(");
        for (Data dataItem : data) {
            builder.append(dataItem);
        }
        builder.append(")");
        if (next != null)
            builder.append(next);
        return builder.toString();
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        Worm worm = new Worm(6, 'a');
        Util.println("Worm" + worm);
        ObjectOutputStream objectOut = new ObjectOutputStream(new FileOutputStream("worm.out"));
        objectOut.writeObject("Worm storage\n");
        objectOut.writeObject(worm);
        objectOut.close();

        ObjectInputStream objectIn = new ObjectInputStream(new FileInputStream("worm.out"));
        String string = (String) objectIn.readObject();
        Worm worm2 = (Worm) objectIn.readObject();
        Util.println(string + " worm2 = " + worm2);


        ByteArrayOutputStream bout = new ByteArrayOutputStream();
        ObjectOutputStream out = new ObjectOutputStream(bout);
        out.writeObject("Worm storage2\n");
        out.writeObject(worm);
        out.flush();

        ObjectInputStream in = new ObjectInputStream(new ByteArrayInputStream(bout.toByteArray()));
        String string2 = (String) in.readObject();
        Worm worm3 = (Worm) in.readObject();
        Util.println(string2 + " worm3 = " + worm3);
    }
}
