package com.tr.file.util.Lombok;


import lombok.Cleanup;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class LombokCleanupTest {


    /**
     * 使用了注解的情况
     */
    public static void one(String[] args){


        try {
            @Cleanup InputStream inputStream = new FileInputStream(args[0]);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }

    }

    /**
     * 没有使用注解的情况
     */

    public static void two(String[] args){

        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream(args[0]);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }




}
