package com.ryan.generic.erase;

import com.ryan.util.Util;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by MUFCRyan on 2017/4/6.
 * 边界处的动作——容器
 */

public class ListMaker<T> {
    private Class<T> kind;
    public ListMaker(Class<T> kind){
        this.kind = kind;
    }

    @SuppressWarnings("unchecked")
    List<T> create(int size){
        return new ArrayList<>(size);
    }

    public static void main(String[] args){
        ListMaker<String> stringMaker = new ListMaker<>(String.class);
        List<String> list = stringMaker.create(10);
        Util.println(list.toString());
    }
}
