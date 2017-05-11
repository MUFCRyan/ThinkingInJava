package com.ryan.io.new_io;

import com.ryan.util.Util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by MUFCRyan on 2017/5/11.
 * Book Page581 Chapter 18.12.3 使用“持久性”
 * 通过字节数组来使用对象序列化 --> 从而实现任何可 Serializable 对象的深度复制（deep copy） —— 意味着复制的不仅仅是基本对象及其引用，而是整个对象网
 */

class House implements Serializable{}

class Animal implements Serializable{
    private String name;
    private House preferredHouse;
    Animal(String name, House preferredHouse){
        this.name = name;
        this.preferredHouse = preferredHouse;
    }

    @Override
    public String toString() {
        return name + "[" + super.toString() + "], " + preferredHouse + "\n";
    }
}

public class MyWorld {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        House house = new House();
        List<Animal> animals = new ArrayList<>();
        animals.add(new Animal("Bosco the dog", house));
        animals.add(new Animal("Ralph the hamster", house));
        animals.add(new Animal("Molly the cat", house));
        Util.println("animals: " + animals);
        ByteArrayOutputStream buff1 = new ByteArrayOutputStream();
        ObjectOutputStream out1 = new ObjectOutputStream(buff1);
        out1.writeObject(animals);
        out1.writeObject(animals); // Even if it writes twice but they are the same object

        ByteArrayOutputStream buff2 = new ByteArrayOutputStream();
        ObjectOutputStream out2 = new ObjectOutputStream(buff2);
        out2.writeObject(animals);

        ObjectInputStream in1 = new ObjectInputStream(new ByteArrayInputStream(buff1.toByteArray()));
        ObjectInputStream in2 = new ObjectInputStream(new ByteArrayInputStream(buff2.toByteArray()));
        List<Animal>
                list1 = (List<Animal>) in1.readObject(),
                list2 = (List<Animal>) in1.readObject(),
                list3 = (List<Animal>) in2.readObject();

        Util.println("animals1: " + list1);
        Util.println("animals2: " + list2);
        Util.println("animals3: " + list3);
    }
}
