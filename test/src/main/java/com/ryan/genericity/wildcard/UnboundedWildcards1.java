package com.ryan.genericity.wildcard;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by MUFCRyan on 2017/4/12.
 * 通配符？表示可以持有任何类型
 */

public class UnboundedWildcards1 {
    static List list1;
    static List<?> list2;
    static List<? extends Object> list3;
    static void assign1(List list){
        list1 = list;
        list2 = list;
        list3 = list;
    }

    static void assign2(List<?> list){
        list1 = list;
        list2 = list;
        list3 = list;
    }

    static void assign3(List<? extends Object>  list){
        list1 = list;
        list2 = list;
        list3 = list;
    }

    public static void main(String[] args){
        assign1(new ArrayList());
        assign2(new ArrayList());
        assign3(new ArrayList());

        assign1(new ArrayList<String>());
        assign2(new ArrayList<String>());
        assign3(new ArrayList<String>());

        List<?> wildcard = new ArrayList<String>();
        assign1(wildcard);
        assign2(wildcard);
        assign3(wildcard);
    }
}
