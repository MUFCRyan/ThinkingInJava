package com.ryan.enumerated;

import java.util.Random;

/**
 * Created by MUFCRyan on 2017/5/13.
 * Book Page609 Chapter 19.10.2 使用 enum 状态机
 * enum 非常适于创建状态机；一个状态机可以具有有限个特定的状态，通常根据输入进行状态间的切换；
 * 同时可能存在瞬时状态（transient state）：一旦任务结束，状态机就会立刻离开瞬时状态；
 */

public enum Input {
    NICKEL(5), DIME(10), QUARTER(25), DOLLAR(100), TOOTHPASTE(200), CHIPS(75), SODA(100), SOAP(50),
    ABORT_TRANSACTION {
        @Override
        int amount() { // Disallow
            throw new RuntimeException("ABORT.amount()");
        }
    },
    STOP { // This must be the last instance
        @Override
        int amount() { // Disallow
            throw new RuntimeException("SHUT_DOWN.amount()");
        }
    };

    int value; // In cents

    int amount() {
        return value;
    }

    Input() {
    }

    Input(int value) {
        this.value = value;
    }

    static Random sRandom = new Random(47);
    public static Input randomSelection(){
        return values()[sRandom.nextInt(values().length - 1)];
    }
}
