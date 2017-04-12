package com.ryan.genericity.erase;

import com.ryan.util.Util;

/**
 * Created by MUFCRyan on 2017/4/6.
 */

public class SimpleHolder {
    private Object obj;
    public void set(Object obj){
        this.obj = obj;
    }
    public Object get(){
        return obj;
    }
    public static void main(String[] args){
        SimpleHolder holder = new SimpleHolder();
        holder.set("MUFC");
        String string = (String)holder.get();
        Util.println(string);
    }
}
