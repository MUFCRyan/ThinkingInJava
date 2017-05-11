package com.ryan.enumerated;

import com.ryan.util.Util;

/**
 * Created by MUFCRyan on 2017/5/11.
 * Book Page600 Chapter 19.7
 * 本例只是简单组织了一下 Course 和 Meal 的代码，不过可读性更好
 */

public enum Meal2 {
    APPETIZER(Food.Appetizer.class),
    MAIN_COURSE(Food.MainCourse.class),
    DESSERT(Food.Dessert.class),
    COFFEE(Food.Coffee.class);

    public Food[] values;
    Meal2(Class<? extends Food> foodClass) {
        values = foodClass.getEnumConstants();
    }

    public interface Food{
        enum Appetizer implements Food{
            SALAD, SOUP, SPRING_ROLLS
        }

        enum MainCourse implements Food{
            LASAGNE, BURRITO, PAD_THAI, LENTILS, HUMMOUS, VINDALOO
        }

        enum Dessert implements Food{
            TIRAMISU, GELATO, BLACK_FOREST_CAKE, FRUIT, CREME_CARAMEL
        }

        enum Coffee implements Food{
            BLACK_COFFEE, DECAF_COFFEE, ESPRESSO, LATTE, CAPPUCCINO, TEA, HERB_TEA
        }
    }

    public Food randomSelection(){
        return Enums.random(values);
    }

    public static void main(String[] args){
        for (int i = 0; i < 5; i++) {
            for (Meal2 meal2 : Meal2.values()) {
                Food food = meal2.randomSelection();
                Util.println(food);
            }
            Util.println("----------------------------");
        }
    }
}
