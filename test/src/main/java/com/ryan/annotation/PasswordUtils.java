package com.ryan.annotation;

import com.ryan.annotation.custom.UseCase;

import java.util.List;

/**
 * Created by MUFCRyan on 2017/5/15.
 * Book Page621 Chapter 20.1.1 定义注解
 */

public class PasswordUtils {
    @UseCase(id = 47, description = "Password must contain at least one numeric")
    public boolean validatePassword(String password){
        return password.matches("\\W*\\d\\w*");
    }

    @UseCase(id = 48)
    public String encryptPassword(String password){
        return new StringBuilder(password).reverse().toString();
    }

    @UseCase(id = 49, description = "New password can't equal previously used ones")
    public boolean checkForNewPassword(List<String> prePasswords, String password){
        return !prePasswords.contains(password);
    }
}
