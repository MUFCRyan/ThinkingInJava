package com.ryan.io;

import com.ryan.util.Util;

import java.io.File;

/**
 * Created by MUFCRyan on 2017/5/9.
 * Book Page532 Chapter 18.1.3 目录的检查及创建
 */

public class MakeDiectories {
    private static void usage(){
        System.err.println(
                "Usage:MakeDirectories path1 ...\n" +
                        "Creates each path\n" +
                        "Usage:MakeDirectories -d path1 ...\n" +
                        "Deletes each path\n" +
                        "Usage:MakeDirectories -r path1 path2\n" +
                        "Renames from path1 to path2"
        );
        System.exit(1);
    }

    private static void fileData(File file){
        Util.println(
                "Absolute path: " + file.getAbsolutePath() +
                        "\n Can read: " + file.canRead() +
                        "\n Can write: " + file.canWrite() +
                        "\n getName: " + file.getName() +
                        "\n getParent: " + file.getParent() +
                        "\n getPath: " + file.getPath() +
                        "\n length: " + file.length() +
                        "\n lastModified: " + file.lastModified()
        );

        if (file.isDirectory())
            Util.println("File is a directory!");
        else if (file.isFile())
            Util.println("File is a file!");
    }

    public static void main(String[] args){
        if (args.length < 1)
            usage();
        if (args[0].equals("-r")){
            if (args.length != 3)
                usage();
            File oldFile = new File(args[1]),
                    newFile = new File(args[2]);
            oldFile.renameTo(newFile);
            fileData(oldFile);
            fileData(newFile);
            return;
        }
        int count = 0;
        boolean del = false;
        if (args[0].equals("-d")){
            count ++;
            del = true;
        }
        count --;
        while (++count < args.length){
            File file = new File(args[count]);
            if (file.exists()){
                Util.println(file + "exists");
                if (del){
                    Util.println("deleting..." + file);
                    file.delete();
                }
            } else {
                if (!del){
                    file.mkdirs();
                    Util.println("created " + file);
                }
            }
            fileData(file);
        }
    }
}
