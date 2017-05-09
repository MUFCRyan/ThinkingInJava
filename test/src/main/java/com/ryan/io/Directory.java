package com.ryan.io;

import com.ryan.util.PPrint;
import com.ryan.util.Util;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Created by MUFCRyan on 2017/5/9.
 * Book Page528 Chapter 18.1.2 目录使用工具
 */

public class Directory {
    public static File[] local(File dir, String regex){
        return dir.listFiles(new FilenameFilter() {
            private Pattern mPattern = Pattern.compile(regex);
            @Override
            public boolean accept(File file, String name) {
                return mPattern.matcher(new File(name).getName()).matches();
            }
        });
    }

    public static File[] local(String path, String regex){
        return local(new File(path), regex);
    }

    /**
     * A two-tuple for returning a pair of objects
     */
    public static class TreeInfo implements Iterable<File>{
        public List<File> files = new ArrayList<>();
        public List<File> dirs = new ArrayList<>();
        // The default iterable element is the file list
        @Override
        public Iterator<File> iterator() {
            return files.iterator();
        }

        void addAll(TreeInfo treeInfo){
            files.addAll(treeInfo.files);
            dirs.addAll(treeInfo.dirs);
        }

        @Override
        public String toString() {
            return "dirs: " + PPrint.format(dirs) + "\n\nfiles: " + PPrint.format(files);
        }
    }

    public static TreeInfo walk(String start, String regex){
        return recurseDirs(new File(start), regex);
    }

    public static TreeInfo walk(File start, String regex){
        return recurseDirs(start, regex);
    }

    public static TreeInfo walk(String start){
        return recurseDirs(new File(start), ".*");
    }

    public static TreeInfo walk(File start){
        return recurseDirs(start, ".*");
    }

    static TreeInfo recurseDirs(File startDir, String regex){
        TreeInfo info = new TreeInfo();
        for (File item : startDir.listFiles()) {
            if (item.isDirectory()){
                info.dirs.add(item);
                info.addAll(recurseDirs(item, regex));
            } else {
                if (item.getName().matches(regex))
                    info.files.add(item);
            }
        }
        return info;
    }

    public static void main(String[] args){
        if (args.length == 0)
            Util.println(walk("."));
        else
            for (String start : args) {
                Util.println(walk(start));
            }
    }
}
