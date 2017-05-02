package com.ryan.generic.mixin_type;

import com.ryan.util.Util;

import java.util.Date;

/**
 * Created by MUFCRyan on 2017/4/27.
 * Book Page412 Chapter 15.15 混型
 * 混型：最基本的概念是混合多个类的能力 --> 以产生一个可以表示混型中所有类型的类
 * <p>
 * Book Page413 Chapter 15.12.2 与接口混合
 */

interface TimeStamped {
    long getStamp();
}

class TimeStampedImp implements TimeStamped {
    private final long timeStamp;

    public TimeStampedImp() {
        timeStamp = new Date().getTime();
    }

    @Override
    public long getStamp() {
        return timeStamp;
    }
}

interface SerialNumbered {
    long getSerialNumber();
}

class SerialNumberedImp implements SerialNumbered{
    private static long counter = 1;
    private final long serialNumber = counter ++;
    @Override
    public long getSerialNumber() {
        return serialNumber;
    }
}

interface Basic{
    void set(String arg);
    String get();
}

class BasicImp implements Basic{
    private String value;
    @Override
    public void set(String arg) {
        value = arg;
    }

    @Override
    public String get() {
        return value;
    }
}

/**
 * Mixin 类在使用代理，每个混入类型都要求在 Mixin 中有一个相应的域，必须在 Mixin 中编写必须的方法 --> 注意：该类较简单，当混型的复杂度上升时会导致代码量急剧增加
 */
class Mixin extends BasicImp implements TimeStamped, SerialNumbered{
    private final TimeStamped mTimeStamped = new TimeStampedImp();
    private final SerialNumbered mSerialNumbered = new SerialNumberedImp();

    @Override
    public long getStamp() {
        return mTimeStamped.getStamp();
    }

    @Override
    public long getSerialNumber() {
        return mSerialNumbered.getSerialNumber();
    }
}

public class Mixins{
    public static void main(String[] args){
        Mixin mixin1 = new Mixin(), mixin2 = new Mixin();
        mixin1.set("mixin1");
        mixin2.set("mixin2");
        mixin2.set("mixin2");
        Util.println(mixin1.get() + "    " + mixin1.getStamp() + "    " + mixin1.getSerialNumber());
        Util.println(mixin2.get() + "    " + mixin2.getStamp() + "    " + mixin2.getSerialNumber());
    }
}
