package com.tr.file.util.Lombok;

import lombok.SneakyThrows;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

/**
 * 自动抛受检异常，而无需显式在方法上使用throws语句
 */
public class LombokSneakyThrowsTest {


    @SneakyThrows()
    public void read() {
        InputStream inputStream = new FileInputStream("");
    }
    @SneakyThrows
    public void write() {
        throw new UnsupportedEncodingException();
    }


    /**
     *
     * 没有使用这个异常注解的情况下
     */



    public void read2() throws FileNotFoundException {
        InputStream inputStream = new FileInputStream("");
    }
    public void write2() throws UnsupportedEncodingException {
        throw new UnsupportedEncodingException();
    }
}
