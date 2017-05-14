package com.ryan.enumerated;

/**
 * Created by MUFCRyan on 2017/5/14.
 * Book Page615 Chapter 19.11.1 使用 enum 分发：使用构造器初始化每个 enum 实例，并以一组结果作为参数 --> 形成一种类似查询表的结构
 */

public enum RoShamBo2 implements Competitor<RoShamBo2>{ // 通配符不能扩展多个基类，故此处如此使用
    PAPER(Outcome.DRAW, Outcome.LOSE, Outcome.WIN),
    SCISSORS(Outcome.WIN, Outcome.DRAW, Outcome.LOSE),
    ROCK(Outcome.LOSE, Outcome.WIN, Outcome.DRAW);

    private Outcome paper, scissors, rock;

    RoShamBo2(Outcome paper, Outcome scissors, Outcome rock){
        this.paper = paper;
        this.scissors = scissors;
        this.rock = rock;
    }

    @Override
    public Outcome compete(RoShamBo2 competitor) {
        switch(competitor){
            default :
            case PAPER:
                return paper;
            case SCISSORS:
                return scissors;
            case ROCK:
                return rock;
        }
    }

    public static void main(String[] args){
        RoShamBo.play(RoShamBo2.class, 20);
    }
}
