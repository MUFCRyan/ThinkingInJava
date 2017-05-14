package com.ryan.enumerated;

/**
 * Created by MUFCRyan on 2017/5/14.
 * Book Page616 Chapter 19.11.2 使用常量相关方法
 * enum 实例不是类型，不能将其作为方法签名中的参数类型来使用；最好将 enum 用在 switch 语句中；该种方式的代码不够简化，简化代码间 RoShamBo4.java
 */

public enum RoShamBo3 implements Competitor<RoShamBo3>{
    PAPER{
        @Override
        public Outcome compete(RoShamBo3 it) {
            switch (it){
                default:
                case PAPER:
                    return Outcome.DRAW;
                case SCISSORS:
                    return Outcome.LOSE;
                case ROCK:
                    return Outcome.WIN;
            }
        }
    },
    SCISSORS{
        @Override
        public Outcome compete(RoShamBo3 it) {
            switch (it){
                default:
                case PAPER:
                    return Outcome.WIN;
                case SCISSORS:
                    return Outcome.DRAW;
                case ROCK:
                    return Outcome.LOSE;
            }
        }
    },
    ROCK{
        @Override
        public Outcome compete(RoShamBo3 it) {
            switch (it){
                default:
                case PAPER:
                    return Outcome.LOSE;
                case SCISSORS:
                    return Outcome.WIN;
                case ROCK:
                    return Outcome.DRAW;
            }
        }
    };
    public abstract Outcome compete(RoShamBo3 it);
    public static void main(String[] args){
        RoShamBo.play(RoShamBo3.class, 20);
    }
}
