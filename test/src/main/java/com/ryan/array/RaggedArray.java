package com.ryan.array;

import com.ryan.util.Util;

import java.util.Arrays;
import java.util.Random;

/**
 * Created by MUFCRyan on 2017/5/3.
 * Book Page438 Chapter 16.4 多维数组
 * 粗糙数组：数组中构成矩阵的每个向量都可以具有任意长度
 */

public class RaggedArray {
    public static void main(String[] args){
        Random random = new Random(47);
        // 3-D array with varied-length vectors
        int[][][] array = new int[random.nextInt(7)][][];
        for (int i = 0; i < array.length; i++) {
            array[i] = new int[random.nextInt(5)][];
            for (int j = 0; j < array[i].length; j++) {
                array[i][j] = new int[random.nextInt(6)];
            }
        }

        Util.println(Arrays.deepToString(array));
    }
}
