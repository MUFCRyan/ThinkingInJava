package com.ryan.io.new_io;

import com.ryan.util.Util;

import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.Set;
import java.util.SortedMap;

/**
 * Created by MUFCRyan on 2017/5/10.
 * Book Page555 Chapter 18.10.1 转换数据
 */

public class AvailableCharSets {
    public static void main(String[] args){
        SortedMap<String, Charset> charsets = Charset.availableCharsets();
        Set<String> keySet = charsets.keySet();
        Iterator<String> iterator = keySet.iterator();
        while (iterator.hasNext()){
            String key = iterator.next();
            Util.print(key);
            Iterator<String> aliases = charsets.get(key).aliases().iterator();
            if (aliases.hasNext())
                Util.print(": ");
            while (aliases.hasNext()){
                Util.print(aliases.next());
                if (aliases.hasNext())
                    Util.print(", ");
            }
            Util.println();
        }
    }
}
