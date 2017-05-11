package com.ryan.io.new_io;

import com.ryan.util.Util;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Created by MUFCRyan on 2017/5/11.
 * Book Page578 Chapter 18.12.2 序列化的控制：一起使用（必须） transient 关键字（用于关闭序列化）和 Serializable 接口
 */

public class Logon implements Serializable{
    private Date date = new Date();
    private String userName;
    private transient String password;
    public Logon(String userName, String password){
        this.userName = userName;
        this.password = password;
    }

    @Override
    public String toString() {
        return "logon info: \n  username: " + userName + "\n    date: " + date + "\n    password: " + password;
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
        Logon logon = new Logon("MUFCRyan", "123456");
        Util.println("logon: " + logon);
        ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("Logon.out"));
        out.writeObject(logon);
        out.close();

        TimeUnit.SECONDS.sleep(1);
        ObjectInputStream in = new ObjectInputStream(new FileInputStream("Logon.out"));
        Util.println("Recovering object");
        Logon logon1 = (Logon) in.readObject();
        Util.println("logon1: " + logon1);
    }
}
