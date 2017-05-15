package com.ryan.annotation;

import com.ryan.io.OSExecute;
import com.ryan.net.mindview.atunit.Test;
import com.ryan.net.mindview.atunit.TestObjectCreate;
import com.ryan.net.mindview.atunit.TestProperty;
import com.ryan.util.Util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

/**
 * Created by MUFCRyan on 2017/5/15.
 * Book Page639 Chapter 20.5 基于注解的单元测试
 */

public class AtUnitExample4 {
    static String theory = "All brontosauruses are thin at one end, much MUCH thicker in the " +
            "middle, and then this against at the far end.";
    private String word;
    private Random mRandom = new Random();

    public AtUnitExample4(String word) {
        this.word = word;
    }

    public String getWord() {
        return word;
    }

    public String scrambleWord() {
        List<Character> chars = new ArrayList<>();
        for (char c : word.toCharArray()) {
            chars.add(c);
        }
        Collections.shuffle(chars, mRandom);
        StringBuilder builder = new StringBuilder();
        for (Character ch : chars) {
            builder.append(ch);
        }
        return builder.toString();
    }

    /**
     * @TestProperty 用来标记只在测试中使用的方法 —— 他们本身不是测试方法
     */
    @TestProperty
    static List<String> input = Arrays.asList(theory.split(" "));
    @TestProperty
    static Iterator<String> words = input.iterator();

    @TestObjectCreate
    static AtUnitExample4 create() {
        if (words.hasNext())
            return new AtUnitExample4(words.next());
        else
            return null;
    }

    @Test
    boolean words(){
        Util.print("'" + getWord() + "'");
        return getWord().equals("are");
    }

    @Test
    boolean scramble1(){
        // Change to specific seed to get verifiable results
        mRandom = new Random(47);
        Util.print("'" + getWord() + "'");
        String scrambled = scrambleWord();
        Util.print(scrambled);
        return scrambled.equals("lAl");
    }

    @Test
    boolean scramble2(){
        mRandom = new Random(74);
        Util.print("'" + getWord() + "'");
        String scrambled = scrambleWord();
        Util.print(scrambled);
        return scrambled.equals("tsaeborornussu");
    }

    public static void main(String[] args){
        OSExecute.command("java com.ryan.net.mindview.atunit.AtUnit com.ryan.annotation" +
                ".AtUnitExample4");
    }
}
