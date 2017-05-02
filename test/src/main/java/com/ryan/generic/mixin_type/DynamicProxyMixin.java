package com.ryan.generic.mixin_type;

import com.ryan.generic.tuple.Tuple;
import com.ryan.generic.tuple.TwoTuple;
import com.ryan.util.Util;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;

/**
 * !!IMPORTANT
 * Created by MUFCRyan on 2017/4/28.
 * Book Page415 Chapter 15.12.4 与动态代理混合
 * 可以使用动态代理来创建一种比装饰器更贴近混型的模型机制。通过使用动态代理，所产生的动态类型将会是已经混入的组合的类型
 * 由于动态代理的限制，每个被呼入类型必须是某个接口的实现
 */

class MixinProxy implements InvocationHandler{

    Map<String, Object> delegatesByMethod;

    public MixinProxy(TwoTuple<Object, Class<?>>... pairs){
        delegatesByMethod = new HashMap<>();
        for (TwoTuple<Object, Class<?>> pair : pairs) {
            for (Method method : pair.second.getMethods()) {
                String name = method.getName();
                // The first interface in the map implements the method
                if (!delegatesByMethod.containsKey(name)){
                    delegatesByMethod.put(name, pair.first);
                }
            }
        }
    }

    @Override
    public Object invoke(Object o, Method method, Object[] args) throws Throwable {
        String name = method.getName();
        Object delegate = delegatesByMethod.get(name);
        return method.invoke(delegate, args);
    }

    public static Object newInstance(TwoTuple... pairs){
        Class[] interfaces = new Class[pairs.length];
        for (int i = 0; i < pairs.length; i++) {
            interfaces[i] = (Class) pairs[i].second;
        }
        ClassLoader classLoader = pairs[0].first.getClass().getClassLoader();
        return Proxy.newProxyInstance(classLoader, interfaces, new MixinProxy(pairs));
    }
}

/**
 * 只有动态类型才包含所有的混入类型，所以仍旧不如C++，在调用相应的方法前必须向下转型，不过仍旧接近于真正的混型；附加语言 Jam 专门用于支持混型
 */
public class DynamicProxyMixin {
    public static void main(String[] args){
        Object mixin = MixinProxy.newInstance(Tuple.tuple(new BasicImp(), Basic.class),
                Tuple.tuple(new TimeStampedImp(), TimeStamped.class),
                Tuple.tuple(new SerialNumberedImp(), SerialNumbered.class));
        Basic basic = (Basic) mixin;
        TimeStamped timeStamped = (TimeStamped) mixin;
        SerialNumbered serialNumbered = (SerialNumbered) mixin;
        basic.set("Ryan");
        Util.println(basic.get());
        Util.println(timeStamped.getStamp());
        Util.println(serialNumbered.getSerialNumber());
    }
}
