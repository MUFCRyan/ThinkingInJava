package com.ryan.annotation;

import com.ryan.annotation.custom.TestObjectCleanup;
import com.ryan.annotation.custom.TestObjectCreate;
import com.ryan.io.BinaryFile;
import com.ryan.io.ProcessFiles;
import com.ryan.net.mindview.atunit.Test;
import com.ryan.util.Util;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by MUFCRyan on 2017/5/15.
 * Book Page643 Chapter 20.5.3 实现 @Unit
 */

public class AtUnit implements ProcessFiles.Strategy {
    static Class<?> testClass;
    static List<String> failedTests = new ArrayList<>();
    static long testsRun;
    static long failures;

    public static void main(String[] args) {
        ClassLoader.getSystemClassLoader().setDefaultAssertionStatus(true); // Enable asserts
        new ProcessFiles(new AtUnit(), "class").start(args);
        if (failures == 0)
            Util.println("OK (" + testsRun + " tests)");
        else {
            Util.println("(" + testsRun + " tests)");
            Util.println("\n>>> " + failures + " FAILURE" + (failures > 1 ? "S" : "") + "<<<");
            for (String test : failedTests) {
                Util.print(" " + test);
            }
        }
    }

    @Override
    public void process(File file) {
        try {
            // 一个 class 文件中有可能有其他的与其名字不同的其他类，所以此处使用 ClassNameFinder 进行处理；处理过程：找到 .class 文件并打开 -->
            // 读取其二进制数据 --> 然后将其交给 ClassNameFinder.thisClass()，此处将进入“字节码”工程的领域，因为实际上我们是在分析一个类文件的内容
            String cName = ClassNameFinder.thisClass(BinaryFile.read(file));
            if (!cName.contains("."))
                return;
            testClass = Class.forName(cName);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        TestMethods testMethods = new TestMethods();
        Method creator = null;
        Method cleanup = null;
        for (Method method : testClass.getDeclaredMethods()) {
            testMethods.addIfTestMethod(method);
            if (creator == null)
                creator = checkForCreatorMethod(method);
            if (cleanup == null)
                cleanup = checkForCleanupMethod(method);
        }
        if (testMethods.size() > 0) {
            if (creator == null) {
                try {
                    if (!Modifier.isPublic(testClass.getDeclaredConstructor().getModifiers())) {
                        Util.println("Error: " + testClass + " default constructor must be public");
                        System.exit(1);
                    }
                } catch (NoSuchMethodException e) {
                    // Synthesized default constructor: OK
                }
                Util.println(testClass.getName());
            }

            for (Method method : testMethods) {
                Util.println("  . " + method.getName() + " ");
                Object testObject = createTestObject(creator);
                boolean success = false;
                try {
                    if (method.getReturnType().equals(boolean.class))
                        success = (Boolean) method.invoke(testObject);
                    else {
                        method.invoke(testObject);
                        success = true; // If no assert fails
                    }
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    Util.println(e.getCause());
                }
                Util.println(success ? "" : "(failed)");
                testsRun++;
                if (!success) {
                    failures++;
                    failedTests.add(testClass.getName() + ": " + method.getName());
                }
                try {
                    if (cleanup != null)
                        cleanup.invoke(testObject, testObject);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    static class TestMethods extends ArrayList<Method> {
        void addIfTestMethod(Method method) {
            if (method.getAnnotation(Test.class) == null)
                return;
            if (!method.getReturnType().equals(boolean.class) || method.getReturnType().equals(void.class)) {
                throw new RuntimeException("@Test method must return boolean or void");
            }
            method.setAccessible(true);
            add(method);

        }
    }

    private Method checkForCreatorMethod(Method method) {
        if (method.getAnnotation(TestObjectCreate.class) == null)
            return null;
        if (!method.getReturnType().equals(testClass))
            throw new RuntimeException("@TestObjectCreate must return instance of Class to be tested");
        if ((method.getModifiers() & Modifier.STATIC) < 1)
            throw new RuntimeException("@TestObjectCreate must be static");
        method.setAccessible(true);
        return method;
    }

    private Method checkForCleanupMethod(Method method) {
        if (method.getAnnotation(TestObjectCleanup.class) == null)
            return null;
        if (!method.getReturnType().equals(void.class))
            throw new RuntimeException("@TestObjectCleanup must return void");
        if ((method.getModifiers() & Modifier.STATIC) < 1)
            throw new RuntimeException("@TestObjectCleanup must be static");
        if (method.getParameterTypes().length == 0 || method.getParameterTypes()[0] != testClass)
            throw new RuntimeException("@TestObjectCleanup must take an argument of the tested type");
        method.setAccessible(true);
        return method;
    }

    private Object createTestObject(Method creator) {
        if (creator != null){
            try {
                return creator.invoke(testClass);
            } catch (Exception e) {
                throw new RuntimeException("Couldn't run @TestObject (creator) method.");
            }
        } else {
            try {
                return testClass.newInstance();
            } catch (Exception e) {
                throw new RuntimeException("Couldn't create a test object. Try using a @TestObject method.");
            }
        }
    }
}
