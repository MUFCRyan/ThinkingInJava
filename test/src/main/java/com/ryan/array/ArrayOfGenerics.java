package com.ryan.array;

import com.ryan.generic.coffee.Coffee;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by MUFCRyan on 2017/5/3.
 * Book Page440 Chapter 16.5 数组与泛型
 * 此外可以创建对泛型数组的引用来代替对其的创建（List<String>[] list） —— 可以创建非泛型数组并将其转型
 */

public class ArrayOfGenerics {
    public static void main(String[] args){
        List<String>[] list;
        List[] la = new List[10];
        list = (List<String>[]) la; // "Unchecked" warning
        list[0] = new ArrayList<String>();
        // Compile-time checking produces an error
        // /list[1] = new ArrayList<Integer>();

        // Can produces a List<String>[] objects, but here is creates a Object[] objects for the
        // next tests
        Object[] objects = list;
        objects[1] = new ArrayList<Integer>();

        List<Coffee>[] coffees = /*(List<Coffee>[]) */new List[10]; // Can do this and can force
        // to transition to List<Coffee>[]
        for (int i = 0; i < coffees.length; i++) {
            coffees[i] = new ArrayList<Coffee>();
        }
    }
}
