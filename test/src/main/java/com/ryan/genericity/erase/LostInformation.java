package com.ryan.genericity.erase;

import com.ryan.util.Util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *  Created by MUFCRyan on 2017/4/5.
 */

public class LostInformation {
    public static void main(String[] args){
        List<Frob> frobs = new ArrayList<>();
        Map<Frob, Fnorkle> map = new HashMap<>();
        Quark<Fnorkle> quark = new Quark<>();
        Particle<Long, Double> particle = new Particle<>();
        Util.println(Arrays.toString(frobs.getClass().getTypeParameters()));
        Util.println(Arrays.toString(map.getClass().getTypeParameters()));
        Util.println(Arrays.toString(quark.getClass().getTypeParameters()));
        Util.println(Arrays.toString(particle.getClass().getTypeParameters()));
    }
}

class Frob {}
class Fnorkle {}
class Quark<Q> {}
class Particle<POSITION, MOMENTUM> {}
