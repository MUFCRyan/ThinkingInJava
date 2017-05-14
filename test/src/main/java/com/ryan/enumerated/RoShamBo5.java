package com.ryan.enumerated;

import java.util.EnumMap;

/**
 * Created by MUFCRyan on 2017/5/14.
 * Book Page617 Chapter 19.11.3 使用 EnumMap 分发：能够真正的实现两路分发
 */

public enum RoShamBo5 implements Competitor<RoShamBo5>{
    PAPER, SCISSORS, ROCK;
    static EnumMap<RoShamBo5, EnumMap<RoShamBo5, Outcome>> table = new EnumMap<>(RoShamBo5.class);
    static {
        for (RoShamBo5 roShamBo5 : RoShamBo5.values()) {
            table.put(roShamBo5, new EnumMap<>(RoShamBo5.class));
        }
        initRow(PAPER, Outcome.DRAW, Outcome.LOSE, Outcome.WIN);
        initRow(SCISSORS, Outcome.WIN, Outcome.DRAW, Outcome.LOSE);
        initRow(ROCK, Outcome.LOSE, Outcome.WIN, Outcome.DRAW);
    }

    static void initRow(RoShamBo5 roShamBo5, Outcome paper, Outcome scissors, Outcome rock){
        EnumMap<RoShamBo5, Outcome> row = table.get(roShamBo5);
        row.put(RoShamBo5.PAPER, paper);
        row.put(RoShamBo5.SCISSORS, scissors);
        row.put(RoShamBo5.ROCK, rock);
    }

    @Override
    public Outcome compete(RoShamBo5 competitor) {
        return table.get(this).get(competitor);
    }

    public static void main(String[] args){
        RoShamBo.play(RoShamBo5.class, 20);
    }
}
