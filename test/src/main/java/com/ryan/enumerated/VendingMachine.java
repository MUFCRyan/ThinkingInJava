package com.ryan.enumerated;

import com.ryan.generic.generator.Generator;
import com.ryan.io.TextFile;
import com.ryan.util.Util;

import java.util.EnumMap;
import java.util.Iterator;

import static com.ryan.enumerated.Category.categorize;
import static com.ryan.enumerated.Input.ABORT_TRANSACTION;
import static com.ryan.enumerated.Input.CHIPS;
import static com.ryan.enumerated.Input.DIME;
import static com.ryan.enumerated.Input.DOLLAR;
import static com.ryan.enumerated.Input.NICKEL;
import static com.ryan.enumerated.Input.QUARTER;
import static com.ryan.enumerated.Input.SOAP;
import static com.ryan.enumerated.Input.SODA;
import static com.ryan.enumerated.Input.STOP;
import static com.ryan.enumerated.Input.TOOTHPASTE;

/**
 * Created by MUFCRyan on 2017/5/13.
 * Book Page610 Chapter 19.10.2 使用 enum 状态机；缺陷：一个机器上只能有一个这样的实例，不过这符合实际情况
 */

enum Category{
    MONEY(NICKEL, DIME, QUARTER, DOLLAR),
    ITEM_SELECTION(TOOTHPASTE, CHIPS, SODA, SOAP),
    QUIT_TRANSACTION(ABORT_TRANSACTION),
    SHUT_DOWN(STOP);
    private Input[] mTypes;
    Category(Input... types) {
        mTypes = types;
    }
    private static EnumMap<Input, Category> sCategories = new EnumMap<>(Input.class);
    static {
        for (Category category : Category.class.getEnumConstants()) {
            for (Input type : category.mTypes) {
                sCategories.put(type, category);
            }
        }
    }
    public static Category categorize(Input input){
        return sCategories.get(input);
    }
}

/**
 * 原理是 enum 的定义序列
 */
public class VendingMachine {
    enum StateDuration{
        TRANSIENT
    }
    enum State{
        RESTING {
            @Override
            void next(Input input) {
                switch(categorize(input)){
                    case MONEY:
                        amount += input.amount();
                        state = ADDING_MONEY;
                        break;
                    case SHUT_DOWN:
                        state = TERMINAL;
                        break;
                    default :
                        break;
                }
            }
        },
        ADDING_MONEY{
            @Override
            void next(Input input) {
                switch(categorize(input)){
                    case MONEY:
                        amount += input.amount();
                        break;
                    case ITEM_SELECTION:
                        selection = input;
                        if (amount < selection.amount())
                            Util.println("Insufficient money of " + selection);
                        else
                            state = DISPENSING;
                        break;
                    case QUIT_TRANSACTION:
                        state = GIVING_CHANGE;
                        break;
                    case SHUT_DOWN:
                        state = TERMINAL;
                        break;
                    default :
                        break;
                }
            }
        },
        DISPENSING(StateDuration.TRANSIENT){
            @Override
            void next() {
                Util.println("Here is your " + selection);
                amount -= selection.amount();
                state = GIVING_CHANGE;
            }
        },
        GIVING_CHANGE(StateDuration.TRANSIENT){
            @Override
            void next() {
                if (amount > 0){
                    Util.println("Your change: " + amount);
                    amount = 0;
                }
                state = RESTING;
            }
        },
        TERMINAL{
            @Override
            void output() {
                Util.println("Halted");
            }
        };

        private boolean mIsTransient = false;

        State(){}

        State(StateDuration duration) {
            mIsTransient = true;
        }

        void next(Input input){
            throw new RuntimeException("Only call next(Input input) for non-transient states");
        }

        void next(){
            throw new RuntimeException("Only call next() for StateDuration.TRANSIENT states");
        }

        void output(){
            Util.println(amount);
        }
    }

    private static State state = State.RESTING;
    private static int amount = 0;
    private static Input selection = null;

    static void run(Generator<Input> generator){
        while (state != State.TERMINAL){
            state.next(generator.next());
            while (state.mIsTransient)
                state.next();
            state.output();
        }
    }

    public static void main(String[] args){
        Generator<Input> generator = new RandomInputGenerator();
        if (args.length == 1)
            generator = new FileInputGenerator(args[0]);
        run(generator);
    }
}

class RandomInputGenerator implements Generator<Input> {

    @Override
    public Input next() {
        return Input.randomSelection();
    }
}

class FileInputGenerator implements Generator<Input>{
    private Iterator<String> input;
    public FileInputGenerator(String fileName){
        input = new TextFile(fileName, ";").iterator();
    }
    @Override
    public Input next() {
        if (!input.hasNext())
            return null;
        return Enum.valueOf(Input.class, input.next().trim());
    }
}
