package com.ryan.io;

/**
 * Created by MUFCRyan on 2017/5/9.
 * Book Page550 Chapter 18.9 进程控制
 */

public class OSExecuteException extends RuntimeException{
    public OSExecuteException(String why){
        super(why);
    }
}
