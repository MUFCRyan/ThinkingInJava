package com.ryan.annotation;

import com.ryan.io.OSExecute;
import com.ryan.net.mindview.atunit.Test;

import java.util.HashSet;

/**
 * Created by MUFCRyan on 2017/5/15.
 * Book Page637 Chapter 20.5 基于注解的单元测试
 */

public class HashSetTest {
    HashSet<String> testObject = new HashSet<>();

    @Test
    void initialize(){
        assert testObject.isEmpty();
    }

    @Test
    void _contains(){
        testObject.add("one");
        assert testObject.contains("one");
    }

    @Test
    void _remove(){
        testObject.add("one");
        testObject.remove("one");
        assert testObject.isEmpty();
    }

    public static void main(String[] args){
        OSExecute.command("java com.ryan.annotation HashSetTest");
    }
}
