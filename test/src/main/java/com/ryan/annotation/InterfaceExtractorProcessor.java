package com.ryan.annotation;

import com.sun.mirror.apt.AnnotationProcessor;
import com.sun.mirror.apt.AnnotationProcessorEnvironment;
import com.sun.mirror.declaration.MethodDeclaration;
import com.sun.mirror.declaration.Modifier;
import com.sun.mirror.declaration.ParameterDeclaration;
import com.sun.mirror.declaration.TypeDeclaration;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

/**
 * Created by MUFCRyan on 2017/5/15.
 * Book Page630 Chapter 20.3 使用 apt 处理注解
 */

public class InterfaceExtractorProcessor implements AnnotationProcessor{
    private final AnnotationProcessorEnvironment env;
    private ArrayList<MethodDeclaration> interfaceMethods = new ArrayList<>();
    InterfaceExtractorProcessor(AnnotationProcessorEnvironment env){
        this.env = env;
    }
    @Override
    public void process() {
        for (TypeDeclaration typeDeclaration : env.getSpecifiedTypeDeclarations()) {
            ExtractInterface annotation = typeDeclaration.getAnnotation(ExtractInterface.class);
            if (annotation == null)
                break;
            for (MethodDeclaration methodDeclaration : typeDeclaration.getMethods()) {
                if (methodDeclaration.getModifiers().contains(Modifier.PUBLIC)
                        && !methodDeclaration.getModifiers().contains(Modifier.STATIC)){
                    interfaceMethods.add(methodDeclaration);
                }
            }
            if (interfaceMethods.size() > 0){
                try {
                    PrintWriter writer = env.getFiler().createSourceFile(annotation.value());
                    writer.println("package " + typeDeclaration.getPackage().getQualifiedName() + ";");
                    writer.println("public interface " + annotation.value() + "{");
                    for (MethodDeclaration method : interfaceMethods) {
                        writer.print("  public ");
                        writer.print(method.getReturnType() + " ");
                        writer.print(method.getSimpleName() + " (");
                        int i = 0;
                        for (ParameterDeclaration param : method.getParameters()) {
                            writer.print(param.getType() + " " + param.getSimpleName());
                            if (++i < method.getParameters().size()){
                                writer.print(", ");
                            }
                        }
                        writer.println(");");
                    }
                    writer.println("}");
                    writer.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}
