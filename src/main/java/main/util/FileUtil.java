package main.util;

import java.io.File;
import java.util.*;
import java.util.stream.Collectors;

public class FileUtil {
    public static List<String> getFiles(String path) {
        List<File> files = Arrays.asList(new File(path).listFiles());
        files.sort((o1, o2) -> {
            if (o1.isDirectory() && o2.isFile()) {
                return -1;
            }
            if (o1.isFile() && o2.isDirectory()) {
                return 1;
            }
            Integer f = f(o1.getName());
            Integer f2 = f(o2.getName());
            return Integer.compare(f, f2);
        });
        return files.stream().map(File::toString).collect(Collectors.toList());
    }

    static Integer f(String filename) {
        int x = filename.indexOf(".");
        String string2 = filename.substring(0, x);
        char[] cs = string2.toCharArray();
        StringBuilder builder = new StringBuilder();
        for (char c : cs) {
            if (Character.isDigit(c)) {
                builder.append(c);
            }
        }
        return Integer.parseInt(builder.toString());
    }


    public static boolean createDir(String destDirName) {
        File dir = new File(destDirName);
        if (dir.exists()) {
            System.out.println("创建目录" + destDirName + "失败，目标目录已经存在");
            return false;
        }
        if (!destDirName.endsWith(File.separator)) {
            destDirName = destDirName + File.separator;
        }
        //创建目录
        if (dir.mkdirs()) {
            System.out.println("创建目录" + destDirName + "成功！");
            return true;
        } else {
            System.out.println("创建目录" + destDirName + "失败！");
            return false;
        }
    }
    /**
     * 先根遍历序递归删除文件夹
     */
    public static boolean deleteFile(String destDirName) {
        File dirFile = new File(destDirName);

        // 如果dir对应的文件不存在，则退出
        if (!dirFile.exists()) {
            return false;
        }

        if (dirFile.isFile()) {
            return dirFile.delete();
        } else {

            for (File file : dirFile.listFiles()) {
                deleteFile(file.toString());
            }
        }

        return dirFile.delete();
    }

}
