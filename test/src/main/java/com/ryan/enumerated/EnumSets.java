package com.ryan.enumerated;

import com.ryan.util.Util;

import java.util.EnumSet;

/**
 * Created by MUFCRyan on 2017/5/12.
 * Book Page600 Chapter 19.8 EnumSet：优点在于其说明一个二进制位是否存在时具有更好的表达能力、无需担心性能问题
 * EnumSet 的基础是 long，一个 long 包含64个 bit 位，一个 enum 只需一个 bit 位表示其是否存在 --> 不超过一个 long 的表达能力的情况下
 * EnumSet 最多可以应用于64个元素的 enum，即便超过也会自动扩容
 * enum 定义时的次序决定了其在 EnumSet/Map 中的顺序，此外对于同一个元素重复调用 add() 方法会被忽略掉
 */

enum AlarmPoints{
    STAIR1, STAIR2, LOBBY, OFFICE1, OFFICE2, OFFICE3, OFFICE4, BATHROOM, UTILITY, KITCHEN
}

public class EnumSets {
    public static void main(String[] args){
        EnumSet<AlarmPoints> points = EnumSet.noneOf(AlarmPoints.class); // Empty set
        Util.println(points);
        points.add(AlarmPoints.BATHROOM);
        Util.println(points);
        points.addAll(EnumSet.of(AlarmPoints.STAIR1, AlarmPoints.STAIR2, AlarmPoints.UTILITY));
        Util.println(points);
        points = EnumSet.allOf(AlarmPoints.class);
        points.removeAll(EnumSet.of(AlarmPoints.STAIR1, AlarmPoints.STAIR2, AlarmPoints.UTILITY));
        Util.println("of : " + points);
        points.removeAll(EnumSet.range(AlarmPoints.OFFICE1, AlarmPoints.OFFICE4));
        Util.println("range : " + points);
        points = EnumSet.complementOf(points);
        Util.println(points);
    }
}
