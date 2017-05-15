package com.ryan.annotation;

import com.ryan.annotation.database.Constraints;
import com.ryan.annotation.database.DBTable;
import com.ryan.annotation.database.Member;
import com.ryan.annotation.database.SQLInteger;
import com.ryan.annotation.database.SQLString;
import com.ryan.util.Util;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by MUFCRyan on 2017/5/15.
 * Book Page625 Chapter 20.2.5 实现处理器
 */

public class TableCreator {
    public static void main(String[] args) throws ClassNotFoundException {
        args = new String[1];
        args[0] = "Member";

        if (args.length < 1){
            Util.println("arguments: annotated classes");
            System.exit(0);
        }

        for (String className : args) {
            Class<?> aClass = Member.class;
            DBTable annotation = aClass.getAnnotation(DBTable.class);
            if (annotation == null){
                Util.println("No DBTable annotations in class " + className);
                continue;
            }
            String tableName = annotation.name();
            // If the name is empty, use the Class name
            if (tableName.isEmpty()){
                tableName = aClass.getName().toUpperCase();
            }
            List<String> columnDefs = new ArrayList<>();
            for (Field field : aClass.getDeclaredFields()) {
                String columnName = null;
                Annotation[] fieldAnnotations = field.getDeclaredAnnotations(); // 因为没有继承机制，该方法是获得近似多态行为的唯一方式
                if (fieldAnnotations.length < 1)
                    continue; // Not a db column
                if (fieldAnnotations[0] instanceof SQLInteger){
                    SQLInteger integer = (SQLInteger) fieldAnnotations[0];
                    // Use field name if name not specified
                    if (integer.name().length() < 1)
                        columnName = field.getName().toUpperCase();
                    else
                        columnName = integer.name();
                    columnDefs.add(columnName + " INT" + getConstraints(integer.constraints()));
                }

                if (fieldAnnotations[0] instanceof SQLString){
                    SQLString string = (SQLString) fieldAnnotations[0];
                    // Use field name if name not specified
                    if (string.name().length() < 1)
                        columnName = field.getName().toUpperCase();
                    else
                        columnName = string.name();
                    columnDefs.add(columnName + " VARCHAR(" + string.value() + ")" + getConstraints(string.constraints()));
                }

                StringBuilder createCommand = new StringBuilder("CREATE TABLE " + tableName + "(");
                for (String def : columnDefs) {
                    createCommand.append("\n    " + def + ",");
                }
                // Remove trailing comma
                String tableCreate = createCommand.substring(0, createCommand.length() - 1) + ")";
                Util.println("Table Creation SQL for " + className + " is :\n" + tableCreate);
            }
        }
    }

    private static String getConstraints(Constraints con) {
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
