package com.ryan.annotation;

import com.ryan.annotation.database.Constraints;
import com.ryan.annotation.database.DBTable;
import com.ryan.annotation.database.SQLInteger;
import com.ryan.annotation.database.SQLString;
import com.sun.mirror.apt.AnnotationProcessor;
import com.sun.mirror.apt.AnnotationProcessorEnvironment;
import com.sun.mirror.apt.AnnotationProcessorFactory;
import com.sun.mirror.declaration.AnnotationTypeDeclaration;
import com.sun.mirror.declaration.ClassDeclaration;
import com.sun.mirror.declaration.FieldDeclaration;
import com.sun.mirror.declaration.TypeDeclaration;
import com.sun.mirror.util.SimpleDeclarationVisitor;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Set;

import static com.sun.mirror.util.DeclarationVisitors.NO_OP;
import static com.sun.mirror.util.DeclarationVisitors.getDeclarationScanner;

/**
 * Created by MUFCRyan on 2017/5/15.
 * Book Page631 Chapter 20.4 将观察者模式用于 apt
 * mirror API
 * 提供了对访问者设计模式的支持：一个访问者会遍历某个数据集合或一个对象的集合，对其中的每个对象执行一个操作；该数据结构无需有序，对于每个对象执行的操作都是特定于此对象的类型 -->
 * 这样就将操作与对象解耦 --> 可以添加新的操作而无需向类的定义中添加方法
 */

public class TableCreationProcessorFactory implements AnnotationProcessorFactory{

    @Override
    public Collection<String> supportedOptions() {
        return Collections.emptySet();
    }

    @Override
    public Collection<String> supportedAnnotationTypes() {
        return Arrays.asList("com.ryan.annotation.DBTable", "com.ryan.annotation.Constraints", "com.ryan.annotation.SQLString", "com.ryan.annotation.SQLInteger");
    }

    @Override
    public AnnotationProcessor getProcessorFor(Set<AnnotationTypeDeclaration> set, AnnotationProcessorEnvironment annotationProcessorEnvironment) {
        return new TableCreationProcessor(annotationProcessorEnvironment);
    }

    public static class TableCreationProcessor implements AnnotationProcessor {
        private AnnotationProcessorEnvironment env;
        private String sql = "";
        TableCreationProcessor(AnnotationProcessorEnvironment env){
            this.env = env;
        }

        @Override
        public void process() {
            for (TypeDeclaration typeDeclaration : env.getSpecifiedTypeDeclarations()) {
                typeDeclaration.accept(getDeclarationScanner(new TableCreationVisitor(), NO_OP));
            }
        }

        private class TableCreationVisitor extends SimpleDeclarationVisitor{
            @Override
            public void visitClassDeclaration(ClassDeclaration classDeclaration) {
                DBTable dbTable = classDeclaration.getAnnotation(DBTable.class);
                if (dbTable != null){
                    sql += "CREATE TABLE ";
                    sql += (dbTable.name().length() < 1) ? classDeclaration.getSimpleName().toUpperCase() : dbTable.name();
                    sql += " (";
                }
            }

            @Override
            public void visitFieldDeclaration(FieldDeclaration fieldDeclaration) {
                String columnName = "";
                if (fieldDeclaration.getAnnotation(SQLInteger.class) != null){
                    SQLInteger annotation = fieldDeclaration.getAnnotation(SQLInteger.class);
                    // Use field name if name not specified
                    if (annotation.name().length() < 1){
                        columnName = fieldDeclaration.getSimpleName().toUpperCase();
                    } else {
                        columnName = annotation.name();
                    }
                    sql += "\n  " + columnName + " INT" + getConstraints(annotation.constraints()) + ",";
                }
                if (fieldDeclaration.getAnnotation(SQLString.class) != null){
                    SQLString annotation = fieldDeclaration.getAnnotation(SQLString.class);
                    // Use field name if name not specified
                    if (annotation.name().length() < 1){
                        columnName = fieldDeclaration.getSimpleName().toUpperCase();
                    } else {
                        columnName = annotation.name();
                    }
                    sql += "\n  " + columnName + " VARCHAR(" + annotation.value() + ")" +
                            getConstraints(annotation.constraints()) + ",";
                }
            }

            public String getConstraints(Constraints con){
                String constraints = "";
                if (!con.allowNull())
                    constraints += " NOT NULL";
                if (con.primaryKey())
                    constraints += " PRIMARY KEY";
                if (con.unique())
                    constraints += " UNIQUE";
                return constraints;
            }
        }
    }
}
