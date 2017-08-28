package com.lemon.chen.lemon.qiniu;

import com.qiniu.util.Auth;

/**
 * Created by chenhualong on 16/6/13.
 * 下载文件
 */
public class QiNiuDownLoadUtil {



    //密钥配置
    Auth auth = Auth.create(QiNiuConstants.ACCESS_KEY, QiNiuConstants.SECRET_KEY);
    //构造私有空间的需要生成的下载的链接
    String URL = "http://bucketdomain/key";

    public void download(String  url, long time){
        //调用privateDownloadUrl方法生成下载链接,第二个参数可以设置Token的过期时间
        String downloadRUL = auth.privateDownloadUrl(url,time);

    }


}
