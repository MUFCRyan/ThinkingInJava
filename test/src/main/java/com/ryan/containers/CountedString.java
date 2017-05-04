package com.ryan.containers;

import com.ryan.util.Util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by MUFCRyan on 2017/5/4.
 * Book Page495 Chapter 17.9.3 覆盖 hashCode()
 * hashCode() 必须快且有意义 —— 基于对象的内容生成；散列码不必独一无二、但是基于 hashCode() 和 equals() 必须能够完全确定对象的身份
 * 正确 hashCode() 的基本指导：
 *      1. int 变量 result 赋予非零常量
 *      2. 为对象内每一个有意义的域 f（每个可做 equals() 操作的域）计算出一个 int 散列码 c
 *      3. 合并计算得到的散列码：result = 37 * result + c
 *      4. 返回 result
 *      5. 检查 hashCode() 最后生成的结果，确保相同的对象有相同的散列码
 */


public class CountedString {
    private static List<String> created = new ArrayList<>();
    private String s;
    private int id = 0;
    public CountedString(String string){
        this.s = string;
        created.add(s);
        for (String str : created) {
            if (str.equals(s)){
                id ++;
            }
        }
    }

    @Override
    public String toString() {
        return "String：" + s + " id: " + id + " hashCode(): " + hashCode();
    }

    @Override
    public int hashCode() {
        // The very simple approach:
        // Returns s.hashCode * id:
        // Using Joshua Bloch's recipe
        // 各 hashCode 域累加
        int result = 17;
        result = 37 * result + s.hashCode();
        result = 37 * result + id;
        return result;
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof CountedString && s.equals(((CountedString)o).s) && id == ((CountedString)o).id;
    }

    public static void main(String[] args){
        Map<CountedString, Integer> map = new HashMap<>();
        CountedString[] csArray = new CountedString[5];
        for (int i = 0; i < csArray.length; i++) {
            csArray[i] = new CountedString("hello");
            map.put(csArray[i], i);
        }

        Util.println(map);
        for (CountedString string : csArray) {
            Util.println("Looking up " + string);
            Util.println(map.get(string));
        }
    }
}
