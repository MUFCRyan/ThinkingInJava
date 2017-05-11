package com.ryan.enumerated;

import com.ryan.util.Util;

/**
 * Created by MUFCRyan on 2017/5/11.
 * Book Page599 Chapter 19.7
 */

public enum SecurityCategory {
    STOCK(Security.Stock.class),
    BOND(Security.Bond.class);

    private Security[] values;
    SecurityCategory(Class<? extends Security> securityClass) {
        values = securityClass.getEnumConstants();
    }

    /**
     * Security 的作用是将其所包含的 enum 组合成一个公共类型
     */
    interface Security{
        enum Stock implements Security {
            SHORT, LONG, MARGIN
        }

        enum Bond implements Security {
            MUNICIPAL, JUNK
        }
    }

    public Security randomSelection(){
        return Enums.random(values);
    }

    public static void main(String[] args){
        for (int i = 0; i < 10; i++) {
            SecurityCategory category = Enums.random(SecurityCategory.class);
            Util.println(category + ": " + category.randomSelection());
        }
    }
}
