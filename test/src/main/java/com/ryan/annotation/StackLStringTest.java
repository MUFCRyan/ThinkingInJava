package com.ryan.annotation;

import com.ryan.io.OSExecute;

/**
 * Created by MUFCRyan on 2017/5/15.
 * Book Page641 Chapter 20.5.1 将 @Unit 用于泛型
 * 唯一缺点：继承使我们失去了访问被测试的类中的 private 方法的能力，但是可以自行修改
 */

public class StackLStringTest extends StackL {
    void _push(){
        push("one");
        assert top().equals("one");
        push("two");
        assert top().equals("two");
    }

    void _pop(){
        push("one");
        push("two");
        assert pop().equals("two");
        assert pop().equals("one");
    }

    void _top(){
        push("A");
        push("B");
        assert top().equals("B");
        assert top().equals("B");
    }

    public static void main(String[] args){
        OSExecute.command("java com.ryan.net.mindview.atunit.AtUnit com.ryan.annotation.StackLStringTest");
    }
}
