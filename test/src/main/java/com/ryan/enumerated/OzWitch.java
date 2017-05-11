package com.ryan.enumerated;

import com.ryan.util.Util;

/**
 * Created by MUFCRyan on 2017/5/11.
 * Book Page590 Chapter 19.2 向 enum 中添加新方法
 * 除了不能继承自一个 enum 外，基本上可将 enum 看做一个常规类
 */

public enum OzWitch {
    // Instances must be defined first, before methods
    WEST("Miss Gulch, aka the Wicked Witch of the West"),
    NORTH("Glinda, the Good Witch of the North"),
    EAST("Wicked Witch of the East, wearer of the Ruby Slippers, crushed by Dorothy's house"),
    SOUTH("Good by inference, but missing"),;
    private String description;
    // Constructor must be package or private access
    private OzWitch(String description) {
        this.description = description;
    }
    public String getDescription(){
        return description;
    }

    public static void main(String[] args){
        for (OzWitch ozWitch : OzWitch.values()) {
            Util.println(ozWitch.getDescription());
        }
    }
}
