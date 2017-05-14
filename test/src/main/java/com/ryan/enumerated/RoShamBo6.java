package com.ryan.enumerated;

/**
 * Created by MUFCRyan on 2017/5/14.
 * Book Page618 Chapter 19.11.4 使用二维数组（EnumMap 内部也是数组实现）
 * 每个 enum 实例都有一个固定值 --> 故可以使用二维数组将竞争者映射到竞争结果，这种方式能够获得最简洁、直接的解决方案，但是代码比较僵化
 */

public enum RoShamBo6 implements Competitor<RoShamBo6>{
    PAPER, SCISSORS, ROCK;

    public static Outcome[][] table = {
            {Outcome.DRAW, Outcome.LOSE, Outcome.WIN}, // Paper
            {Outcome.WIN, Outcome.DRAW, Outcome.LOSE}, // Scissors
            {Outcome.LOSE, Outcome.WIN, Outcome.DRAW}  // Rock
    };

    @Override
    public Outcome compete(RoShamBo6 competitor) {
        return table[this.ordinal()][competitor.ordinal()];
    }

    public static void main(String[] args){
        RoShamBo.play(RoShamBo6.class, 40);
    }
}
