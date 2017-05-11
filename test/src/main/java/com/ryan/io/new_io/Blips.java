package com.ryan.io.new_io;

import com.ryan.util.Util;

import java.io.Externalizable;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;

/**
 * Created by MUFCRyan on 2017/5/11.
 * Book Page575 Chapter 18.12.2 序列化的控制
 * 方式：1. 实现 Externalizable 接口，在新添加的方法 read/writeExternal() 中处理相关逻辑
 *      2. 一起使用（必须） transient 关键字（用于关闭序列化）和 Serializable 接口
 *      3. 实现 Serializable 接口并自行添加 read/writeObject()，二者的签名必须规定的完全一致：
 *          1. private void readObject(ObjectInputStream stream) throws IOException, ClassNotFoundException;
 *          1. private void writeObject(ObjectOutputStream stream) throws IOException;
 */

/**
 * Externalizable 方式还原时会调用所有的默认构造器（必须是 public 的），缺一不可！
 */
class Blip1 implements Externalizable{
    public Blip1(){
        Util.println("Blip1 constructor");
    }

    @Override
    public void writeExternal(ObjectOutput objectOutput) throws IOException {
        Util.println("Blip1.writeExternal");
    }

    @Override
    public void readExternal(ObjectInput objectInput) throws IOException, ClassNotFoundException {
        Util.println("Blip1.readExternal");
    }
}

class Blip2 implements Externalizable{
    public Blip2(){
        Util.println("Blip2 constructor");
    }

    @Override
    public void writeExternal(ObjectOutput objectOutput) throws IOException {
        Util.println("Blip2.writeExternal");
    }

    @Override
    public void readExternal(ObjectInput objectInput) throws IOException, ClassNotFoundException {
        Util.println("Blip2.readExternal");
    }
}

public class Blips {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        Util.println("Constructing objects");
        Blip1 blip1 = new Blip1();
        Blip2 blip2 = new Blip2();
        ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("Blips.out"));
        out.writeObject(blip1);
        out.writeObject(blip2);
        out.close();

        ObjectInputStream in = new ObjectInputStream(new FileInputStream("Blips.out"));
        Util.println("Recovering object1:");
        Blip1 blip11 = (Blip1) in.readObject();
        Util.println("Recovering object2:");
        Blip2 blip22 = (Blip2) in.readObject();
    }
}
