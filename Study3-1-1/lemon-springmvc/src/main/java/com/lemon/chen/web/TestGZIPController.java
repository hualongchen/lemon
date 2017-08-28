package com.lemon.chen.web;

import com.lemon.chen.lemon.compress.GZipCompressUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;

/**
 * Created by chenhualong on 16/6/7.
 */

@Controller
public class TestGZIPController {


    private static final String ENCODING = "UTF-8";


    /**
     * 浏览器进行交互GZIP的压缩，解压缩
     * @param request
     * @param response
     * @throws Throwable
     */
    @RequestMapping("gzip")
    public void returnGIZDate(HttpServletRequest request,HttpServletResponse response)throws Throwable{


        byte[] data = "我就是喜欢中文".getBytes(ENCODING);

        try {
            byte[] output = GZipCompressUtil.compress(data);

            // 设置Content-Encoding，这是关键点！
            response.setHeader("Content-Encoding", "gzip");
            //兼容IE浏览器
            response.setContentType("text/plain;charset=utf-8");
            // 设置字符集
            response.setCharacterEncoding(ENCODING);
            // 设定输出流中内容长度
            response.setContentLength(output.length);

            OutputStream out = response.getOutputStream();
            out.write(output);
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
