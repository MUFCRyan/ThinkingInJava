package com.ryan.genericity.sets_demo;

import com.ryan.util.Sets;
import com.ryan.util.Util;

import java.util.EnumSet;
import java.util.Set;

import static com.ryan.genericity.sets_demo.WaterColors.*;

/**
 * Created by MUFCRyan on 2017/4/5.
 * 
 */

public class WaterColorSets {
    public static void main(String[] args){
        Set<WaterColors> set1 = EnumSet.range(BRILLIANT_RED, VIRIDIAN_HUE);
        Set<WaterColors> set2 = EnumSet.range(CERULEAN_BLUE_HUE, BURNT_UMBER);
        Set<WaterColors> subSet = Sets.intersection(set1, set2);
        Util.println("set1 : " + set1);
        Util.println("set2 : " + set2);
        Util.println("union : " + Sets.union(set1, set2));
        Util.println("intersection : " + subSet);
        Util.println("difference : " + Sets.difference(set1, set2));
        Util.println("complement : " + Sets.complement(set1, set2));
    }
}
