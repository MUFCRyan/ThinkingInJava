package com.ryan.annotation;

import com.ryan.annotation.custom.UseCase;
import com.ryan.util.Util;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by MUFCRyan on 2017/5/15.
 * Book Page622 Chapter 20.2 编写注解处理器
 * Book Page623 Chapter 20.2.1 注解元素可用类型：所有基本类型、String、Class、enum、Annotation 及这些类型的数组
 */

public class UseCaseTracker {
    public static void trackUseCases(List<Integer> useCases, Class<?> type){
        for (Method method : type.getDeclaredMethods()) {
            UseCase annotation = method.getAnnotation(UseCase.class);
            if (annotation != null){
                Util.println("Found Use Case: " + annotation.id() + " " + annotation.description());
                useCases.remove(new Integer(annotation.id()));
            }
        }

        for (Integer useCase : useCases) {
            Util.println("Warning: Missing use case-" + useCase);
        }
    }

    public static void main(String[] args){
        List<Integer> useCases = new ArrayList<>();
        Collections.addAll(useCases, 47, 48, 49);
        trackUseCases(useCases, UseCase.class);
    }
}
