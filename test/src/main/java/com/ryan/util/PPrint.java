package com.ryan.util;

import java.util.Arrays;
import java.util.Collection;

/**
 * Created by MUFCRyan on 2017/5/9.
 *
 */

public class PPrint {
    public static String format(Collection<?> collection){
        if (collection.size() == 0){
            return "[]";
        }

        StringBuilder result = new StringBuilder("[");
        for (Object elment : collection) {
            if (collection.size() != 1)
                result.append("\n ");
            result.append(elment);
        }
        if (collection.size() != 1)
            result.append("\n ");
        result.append("]");
        return result.toString();
    }

    public static void pprint(Collection<?> collection){
        Util.println(format(collection));
    }

    public static void pprint(Object[] objects){
        Util.println(format(Arrays.asList(objects)));
    }
}
