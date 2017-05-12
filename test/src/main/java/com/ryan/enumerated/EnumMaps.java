package com.ryan.enumerated;

import com.ryan.util.Util;

import java.util.EnumMap;
import java.util.Map;

/**
 * Created by MUFCRyan on 2017/5/12.
 * Book Page602 Chapter 19.9 EnumMap：key 必须是 enum，内部是数组实现，key 总是存在，未使用 put 传入值时默认值为 null
 * 优点：允许改变值对象，而常量相关方法因在编译其就被固定所以无法改变
 */

interface Command{
    void action();
}

public class EnumMaps {
    public static void main(String[] args){
        EnumMap<AlarmPoints, Command> map = new EnumMap<>(AlarmPoints.class);
        map.put(AlarmPoints.KITCHEN, new Command() {
            @Override
            public void action() {
                Util.println("Kitchen fire!");
            }
        });
        map.put(AlarmPoints.BATHROOM, new Command() {
            @Override
            public void action() {
                Util.println("Bathroom alert!");
            }
        });
        for (Map.Entry<AlarmPoints, Command> entry : map.entrySet()) {
            Util.println(entry.getKey());
            entry.getValue().action();
        }
        try {
            map.get(AlarmPoints.UTILITY).action();
        } catch (Exception e){
            Util.println(e);
        }
    }
}
