package com.ryan.generic.mixin_type;

import java.util.Date;

/**
 * Created by MUFCRyan on 2017/4/28.
 * Book Page414 Chapter 15.12.3 使用装饰器模式
 * 装饰器模式使用分层对象来动态透明地向单个对象中添加责任；其指定包装在最初的对象周围的所有对象都具有相同的基本接口；
 * 某些事物是可装饰的，可通过将其它类包装在这个可装饰的对象的四周，来将功能分层 --> 对装饰器的使用时透明的 —— 无论对象是否被装饰，
 * 我们都能拥有一个可以向对象发送公共消息集；装饰类可以添加新方法但是受限
 * 装饰器通过使用组合和形式化结构（可装饰物/装饰器层次结构）来实现，混型是基于继承的 --> 因此可将基于参数化类型的混型当作一种泛型装饰器机制，该机制不需要装饰器设计模式的继承结构
 */

class Basic2{
    private String value;
    public void set(String arg){
        value = arg;
    }
    public String get(){
        return value;
    }
}

class Decorator extends Basic2{
    protected Basic2 mBasic2;
    public Decorator(Basic2 basic2){
        mBasic2 = basic2;
    }
    public void set(String arg){
        mBasic2.set(arg);
    }
    public String get(){
        return mBasic2.get();
    }
}

class TimeStamped2 extends Decorator{
    private final long timeStamped;
    public TimeStamped2(Basic2 basic2){
        super(basic2);
        timeStamped = new Date().getTime();
    }
    public long getTimeStamped(){
        return timeStamped;
    }
}

class SerialNumbered2 extends Decorator{
    private static long counter = 1;
    private final long serialNumber = counter ++;
    public SerialNumbered2(Basic2 basic2) {
        super(basic2);
    }
    public long getSerialNumber(){
        return serialNumber;
    }
}

/**
 * 装饰器可以添加多层，但是只有最后一层（最外面一层）才可视、可用，故其其明显的缺陷就是只能有效工作于装饰层的最后一层，其只是对由混型提出的问题的一种局限的解决方案；
 * 混型则可以有效工作于其内所有的混合类型
 */
public class Decoration {
    public static void main(String[] args){
        TimeStamped2 stamped1 = new TimeStamped2(new Basic2()), stamped2 = new TimeStamped2(new SerialNumbered2(new Basic2()));

        SerialNumbered2 numbered1 = new SerialNumbered2(new Basic2()), numbered2 = new
                SerialNumbered2(new TimeStamped2(new Basic2()));
    }
}
