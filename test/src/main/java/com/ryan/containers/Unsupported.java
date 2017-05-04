package com.ryan.containers;

import com.ryan.util.Util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Created by MUFCRyan on 2017/5/4.
 * Book Page472 Chapter 17.4 可选操作
 * 为了防止接口爆炸：1. UnsupportedOperationException 必须是一种罕见事件；2. 操作若是未获支持的，那么在实现接口时就有可能出现该异常
 * Book Page473 Chapter 17.4.1 未获支持的操作：常见的来源于背后由固定尺寸的数据结构支持的容器
 */

public class Unsupported {
    static void test(String msg, List<String> list){
        Util.println("--- " + msg + " ---");
        Collection<String> collection = list;
        Collection<String> subList = list.subList(1, 8);
        ArrayList<String> collection2 = new ArrayList<>(subList);
        try {
            collection.retainAll(collection2);
        } catch (Exception e){
            Util.println("retainAll(): " + e);
        }

        try {
            collection.removeAll(collection2);
        } catch (Exception e){
            Util.println("removeAll(): " + e);
        }

        try {
            collection.clear();
        } catch (Exception e){
            Util.println("clear(): " + e);
        }

        try {
            collection.add("X");
        } catch (Exception e){
            Util.println("add(): " + e);
        }

        try {
            collection.addAll(collection2);
        } catch (Exception e){
            Util.println("addAll(): " + e);
        }

        try {
            collection.remove("C");
        } catch (Exception e){
            Util.println("remove(): " + e);
        }

        try {
            list.set(0, "X");
        } catch (Exception e){
            Util.println("List.set(): " + e);
        }
    }

    public static void main(String[] args){
        List<String> list = Arrays.asList("A B C D E F G H J K L".split(" "));
        test("Modifiable Copy", new ArrayList<>(list));
        test("Arrays.asList()", list); // 仅支持不修改 List 大小的操作，因其底层是数组，大小不能修改
        test("unmodifiableList()", Collections.unmodifiableList(new ArrayList<>(list)));
    }
}
