package com.ryan.enumerated;

/**
 * Created by MUFCRyan on 2017/5/11.
 * Book Page597 Chapter 19.7 使用接口组织枚举：对于 enum 而言，实现接口是使其子类化的唯一方法
 * 此种方式的缺陷：面对很多类型时使用繁琐，不如 enum 好用
 */

public interface Food {
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

class TypeOfFood{
    public static void main(String[] args){
        Food food = Food.Appetizer.SALAD;
        food = Food.MainCourse.BURRITO;
        food = Food.Dessert.TIRAMISU;
        food = Food.Coffee.LATTE;
    }
}
