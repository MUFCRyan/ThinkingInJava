package com.ryan.enumerated;

import com.ryan.util.Util;

/**
 * Created by MUFCRyan on 2017/5/11.
 * Book Page598 Chapter 19.7 枚举中的枚举
 */

public enum Course {
    APPETIZER(Food.Appetizer.class),
    MAIN_COURSE(Food.MainCourse.class),
    DESSERT(Food.Dessert.class),
    COFFEE(Food.Coffee.class);

    private Food[] values;
    Course(Class<? extends Food> foodClass) {
        values = foodClass.getEnumConstants();
    }

    public Food randomSelection(){
        return Enums.random(values);
    }
}

class Meal{
    public static void main(String[] args){
        for (int i = 0; i < 5; i++) {
            for (Course course : Course.values()) {
                Food food = course.randomSelection();
                Util.println(food);
            }
            Util.println("-----------------------");
        }
    }
}
