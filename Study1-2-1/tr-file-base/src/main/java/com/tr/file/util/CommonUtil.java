package com.tr.file.util;

public class CommonUtil {


    /**
     * 返回文件后缀名字
     * @param fileName
     * @return
     */
    public static String getFileLastName(String fileName){

        int dot = fileName.lastIndexOf('.');

        if ((dot >-1) && (dot < (fileName.length()))) {

            return fileName.substring(dot, fileName.length());
        }else{

            return null ;
        }


    }
}
