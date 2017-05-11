package com.ryan.io.new_io;

import com.ryan.util.Util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * Created by MUFCRyan on 2017/5/11.
 * Book Page580 Chapter 18.12.2 序列化的控制：实现 Serializable 接口并自行添加 read/writeObject()，此种方式可以序列化/反序列化
 * transient 字段
 */

public class SerialCtl implements Serializable{
    private String a;
    private transient String b;
    public SerialCtl(String a, String b){
        this.a = "Not Transient: " + a;
        this.b = "Transient: " + b;
    }

    @Override
    public String toString() {
        return a + "\n" + b;
    }

    private void writeObject(ObjectOutputStream stream) throws IOException {
        stream.defaultWriteObject(); // 默认机制写入对象的非 transient 部分必须第一个调用
        stream.writeObject(b);
    }

    private void readObject(ObjectInputStream stream) throws IOException, ClassNotFoundException {
        stream.defaultReadObject(); // 默认机制读取对象的非 transient 部分必须第一个调用
        b = (String) stream.readObject();
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        SerialCtl serialCtl = new SerialCtl("MUFC", "Ryan");
        Util.println("Before:\n" + serialCtl);
        ByteArrayOutputStream bout = new ByteArrayOutputStream();
        ObjectOutputStream out = new ObjectOutputStream(bout);
        out.writeObject(serialCtl);

        ObjectInputStream in = new ObjectInputStream(new ByteArrayInputStream(bout.toByteArray()));
        SerialCtl serialCtl1 = (SerialCtl) in.readObject();
        Util.println("After:\n" + serialCtl1);
    }
}
