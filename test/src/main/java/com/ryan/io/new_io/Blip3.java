package com.ryan.io.new_io;

import com.ryan.util.Util;

import java.io.Externalizable;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;

/**
 * Created by MUFCRyan on 2017/5/11.
 * Book Page577 Chapter 18.12.2 序列化的控制：完整的保存和恢复 Externalizable 对象
 */

public class Blip3 implements Externalizable{
    private int i;
    private String s;
    public Blip3(){
        // i, s not initialized
        Util.println("Blip3 constructor");
    }
    public Blip3(int i, String s){
        // i & s initialized only in non-default constructor
        this.i = i;
        this.s = s;
    }

    @Override
    public String toString() {
        return s + i;
    }


    @Override
    public void writeExternal(ObjectOutput objectOutput) throws IOException {
        Util.println("Blip3 writeExternal");
        // Have to do this
        objectOutput.writeInt(i);
        objectOutput.writeObject(s);
    }

    @Override
    public void readExternal(ObjectInput objectInput) throws IOException, ClassNotFoundException {
        Util.println("Blip3 readExternal");
        // Have to do this
        i = objectInput.readInt();
        s = (String) objectInput.readObject();
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException{
        Util.println("Constructing objects:");
        Blip3 blip3 = new Blip3(7, "MUFCRyan");
        Util.println(blip3);
        ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("Blip3.out"));
        out.writeObject(blip3);
        out.close();

        ObjectInputStream in = new ObjectInputStream(new FileInputStream("Blip3.out"));
        Util.println("Recovering object");
        Blip3 blip = (Blip3) in.readObject();
        Util.println(blip);
        in.close();
    }
}
