package com.ryan.containers;

import com.ryan.util.Util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by MUFCRyan on 2017/5/3.
 * Book Page459 Chapter 17 容器深入研究
 * Book Page460 Chapter 17.2 填充容器
 * 实用方法：Collections.fill()
 */

class StringAddress{
    private String value;
    public StringAddress(String value){
        this.value = value;
    }

    @Override
    public String toString() {
        return super.toString() + "  " + value;
    }
}

public class FillingLists {
    public static void main(String[] args){
        List<StringAddress> addressList = new ArrayList<>(
                Collections.nCopies(4, new StringAddress("Ryan"))
        );
        Util.println(addressList);

        Collections.fill(addressList, new StringAddress("MUFCRyan"));
        Util.println(addressList);
    }
}
