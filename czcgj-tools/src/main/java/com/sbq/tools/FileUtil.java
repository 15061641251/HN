package com.sbq.tools;

import java.io.File;
import java.io.IOException;

/**
 * 文件工具类
 * Created by zhangyuan on 2017/3/28.
 */
public class FileUtil {

    /**
     * 判断文件是否存在
     * 不存在则创建
     */
    public static void judeFileExists(File file) {

        if (file.exists()) {
            System.out.println("file exists");
        } else {
            System.out.println("file not exists, create it ...");
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 判断文件是否存在
     * 不存在则创建
     */
    public static void judeFileExists(String path) {

        File file = new File(path);
        judeFileExists(file);
    }

    /**
     * 判断文件夹是否存在
     * 不存在则创建
     *
     * @param file
     */
    public static void judeDirExists(File file) {

        if (file.exists()) {
            if (file.isDirectory()) {
                System.out.println("dir exists");
            } else {
                System.out.println("the same name file exists, can not create dir");
            }
        } else {
            System.out.println("dir not exists, create it ...");
            file.mkdir();
        }

    }

    /**
     * 判断文件夹是否存在
     * 不存在则创建
     */
    public static void judeDirExists(String path) {

        File file = new File(path);

        judeDirExists(file);

    }

}
