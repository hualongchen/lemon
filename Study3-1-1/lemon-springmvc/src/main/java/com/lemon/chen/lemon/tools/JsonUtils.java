package com.lemon.chen.lemon.tools;



import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by chenhualong on 16/7/18.
 * 读取线上json配置
 */
public class JsonUtils {


    public static String getYCFile(String urlPath) {
        String readStr = "";
        try {
            try {
                String strUrl = urlPath.trim();
                URL url = new URL(strUrl);
                HttpURLConnection urlCon = (HttpURLConnection) url.openConnection();
                urlCon.setConnectTimeout(10000);
                urlCon.setReadTimeout(30000);
                BufferedReader in = new BufferedReader(new InputStreamReader(
                        urlCon.getInputStream(), "UTF-8"));
                String inputLine = " ";
                while ((inputLine = in.readLine()) != null) {
                    readStr += inputLine.trim();
                }
                in.close();
                return readStr;
            } catch (IOException e) {
                readStr = "";
            }
        } catch (Exception e) {
            readStr = "";
        }
        return readStr;
    }




    public static void main(String[] args) {


        String  returnJson = getYCFile("http://88conf.php.api.zbj.com/get/time.json");


        JSONObject  json  = JSON.parseObject(returnJson);



          for(java.util.Map.Entry<String,Object> entry:json.entrySet()){

              System.out.println(entry.getKey());
              System.out.println(entry.getValue());
          }
    }
}
