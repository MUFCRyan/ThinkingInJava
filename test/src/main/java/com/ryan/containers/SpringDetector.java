package com.ryan.containers;

import com.ryan.util.Util;

import java.lang.reflect.Constructor;
import java.util.HashMap;

/**
 * Created by MUFCRyan on 2017/5/4.
 * Book Page488 Chapter 17.9 散列和散列码
 *
 * 默认的 hshCode() 方法是比较地址的，自定义时需要复写，同时还需要复写 equals()
 * equals() 应满足：1. 自反性；2. 对称性；3. 传递性；4. 一致性；5. 对任何非 null 的 x 有 x.equals(null) 一定返回 false
 */

public class SpringDetector {
    private static <T extends Groundinghog> void detectSpring(Class<T> type) throws Exception {
        Constructor<T> constructor = type.getConstructor(int.class);
        HashMap<Groundinghog, Prediction> map = new HashMap<>();
        for (int i = 0; i < 10; i++) {
            map.put(constructor.newInstance(i), new Prediction());
        }
        Util.println(map);
        Groundinghog gh = constructor.newInstance(3);
        Util.print("Looking up prediction for " + gh);
        if (map.containsKey(gh)){
            Util.println(map.get(gh));
        } else {
            Util.println("Key not found: " + gh);
        }
    }

    public static void main(String[] args) throws Exception {
        detectSpring(Groundinghog.class);
    }
}
