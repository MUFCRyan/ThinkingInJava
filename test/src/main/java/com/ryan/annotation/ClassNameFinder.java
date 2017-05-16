package com.ryan.annotation;

import com.ryan.io.BinaryFile;
import com.ryan.io.Directory;
import com.ryan.util.Util;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by MUFCRyan on 2017/5/16.
 * Book Page646 Chapter 20.5.3 实现 @Unit
 */

public class ClassNameFinder {
    public static String thisClass(byte[] classBytes){
        Map<Integer, Integer> offsetTable = new HashMap<>();
        Map<Integer, String> classNameTable = new HashMap<>();
        try {
            DataInputStream data = new DataInputStream(new ByteArrayInputStream(classBytes));
            int magic = data.readInt(); // 0xcafebabe —— 每个类文件的头32位 bit 都是该数字
            int minorVersion = data.readShort();
            int majorVersion = data.readShort();
            int constant_pool_count = data.readShort();
            int[] constant_pool = new int[constant_pool_count];
            for (int i = 0; i < constant_pool_count; i++) {
                int tag = data.read();
                int tableSize;
                switch(tag){
                    case 1: // UTF
                        int length = data.readShort();
                        char[] bytes = new char[length];
                        for (int j = 0; j < bytes.length; j++) {
                            bytes[j] = (char) data.read();
                        }
                        String className = new String(bytes);
                        classNameTable.put(i, className);
                        break;
                    case 5: // LONG
                    case 6: // DOUBLE
                        data.readLong(); // discard 8 bytes
                        i++; // Special skip necessary
                        break;
                    case 7: // CLASS
                        int offset = data.readShort();
                        offsetTable.put(i, offset);
                        break;
                    case 8: // STRING
                        data.readShort(); // discard 2 bytes
                        break;
                    case 3: // INTEGER
                    case 4: // FLOAT
                    case 9: // FIELD_REF
                    case 10: // METHOD_REF
                    case 11: // INTERFACE_METHOD_REF
                    case 12: // NAME_AND_TYPE
                        data.readInt(); // discard 4 bytes
                        break;
                    default :
                        throw new RuntimeException("Bad tag: " + tag);
                }
            }
            short access_flags = data.readShort();
            int this_class = data.readShort();
            int super_class = data.readShort();
            return classNameTable.get(offsetTable.get(this_class)).replace("/", ".");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // Demonstration
    public static void main(String[] args) throws IOException {
        if (args.length > 0){
            for (String arg : args) {
                Util.print(thisClass(BinaryFile.read(new File(arg))));
            }
        } else {
            // Walk the entire tree
            for (File file : Directory.walk(".", ".*\\.class")) {
                Util.print(thisClass(BinaryFile.read(file)));
            }
        }
    }
}
