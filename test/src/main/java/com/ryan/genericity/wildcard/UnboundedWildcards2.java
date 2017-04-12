package com.ryan.genericity.wildcard;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by MUFCRyan on 2017/4/12.
 * ? 的重要应用：处理多个泛型参数时允许一个参数可以使任意类型，其他参数为某种特定类型
 * List<Object>表示“持有任何Object类型的原生List”，List<?>表示“具有某种特定类型的非原生List，只是我们不知道那种类型是什么”
 */

public class UnboundedWildcards2 {
    static Map map1;
    static Map<?, ?> map2;
    static Map<String, ?> map3;
    static void assign1(Map map){ map1 = map; }
    static void assign2(Map<?, ?> map){ map2 = map; }
    static void assign3(Map<String, ?> map){ map3 = map; }

    public static void main(String[] args){
        assign1(new HashMap());
        assign2(new HashMap());
        assign3(new HashMap());

        assign1(new HashMap<String, Integer>());
        assign2(new HashMap<String, Integer>());
        assign3(new HashMap<String, Integer>());
    }
}
