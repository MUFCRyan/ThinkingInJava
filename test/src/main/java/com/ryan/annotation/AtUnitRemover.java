package com.ryan.annotation;

import com.ryan.io.BinaryFile;
import com.ryan.io.ProcessFiles;
import com.ryan.util.Util;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.bytecode.AnnotationsAttribute;
import javassist.bytecode.MethodInfo;
import javassist.bytecode.annotation.Annotation;

/**
 * Created by MUFCRyan on 2017/5/16.
 * Book Page646 Chapter 20.5.4 移除测试代码
 */

public class AtUnitRemover implements ProcessFiles.Strategy {
    private static boolean remove = false;
    public static void main(String[] args){
        if (args.length > 0 && args[0].equals("-r")){
            remove = true;
            String[] strings = new String[args.length - 1];
            System.arraycopy(args, 1, strings, 0, strings.length);
            args = strings;
        }
        new ProcessFiles(new AtUnitRemover(), "class").start(args);
    }

    @Override
    public void process(File file) {
        boolean modified = false;
        try {
            String className = ClassNameFinder.thisClass(BinaryFile.read(file));
            if (!className.contains("."))
                return; // Ignore unpackaged classes
            ClassPool classPool = ClassPool.getDefault();
            CtClass ctClass = classPool.get(className);
            for (CtMethod method : ctClass.getDeclaredMethods()) {
                MethodInfo info = method.getMethodInfo();
                AnnotationsAttribute attr = (AnnotationsAttribute) info.getAttribute(AnnotationsAttribute.visibleTag);
                if (attr == null)
                    continue;
                for (Annotation annotation : attr.getAnnotations()) {
                    if (annotation.getTypeName().startsWith("com.ryan.annotation")){
                        Util.println(ctClass.getName() + " Method: " + info.getName() + " " + annotation);
                        if (remove){
                            ctClass.removeMethod(method);
                            modified = true;
                        }
                    }
                }
            }

            // Fields are not removed in this version (see text).
            if (modified)
                ctClass.toBytecode(new DataOutputStream(new FileOutputStream(file)));
            ctClass.detach();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
