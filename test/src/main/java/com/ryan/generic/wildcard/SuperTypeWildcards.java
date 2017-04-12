package com.ryan.generic.wildcard;

import java.util.List;

/**
 * Created by MUFCRyan on 2017/4/11.
 * <? extends T>和<? super T>的区别：extends意味着?是T或其子类，在容器中因无法确定所有项都具有该子类所以无法插入其子类，主要用于只读不写（至少是T），T为类型上界；
 * super意味着?是T或其父类直至Object，因无法确定所有项都共有该父类所以无法插入其父类，但可以插入其子类，因为所有项的父类至少是T，T为类型下界，主要用于只写不读（读取时返回Object）
 */

public class SuperTypeWildcards {
    static void writeTo(List<? super Apple> apples){
        // 可添加其本身及其子类，因为所有项至少共有超类Apple，不能添加超类，因为不确定所有项都有该超类
        apples.add(new Apple());
        apples.add(new Jonathon());
        // apples.add(new Fruit()); // 不确定所有项都具有超类Fruit
        // apples.add(new Object()); // 不确定所有项都具有超类Object，即使确实都具有
    }
}
