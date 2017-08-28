package com.lemon.chen.lemon.qiniu;

import com.qiniu.util.Auth;
import com.qiniu.util.StringMap;

import java.io.IOException;

import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.UploadManager;


/**
 * Created by chenhualong on 16/6/13.
 * 上传回调
 */
public class QiNiuReturnBackUploadUtil {




    //密钥配置
    Auth auth = Auth.create(QiNiuConstants.ACCESS_KEY, QiNiuConstants.SECRET_KEY);
    //创建上传对象
    UploadManager uploadManager = new UploadManager();



    /**
     *
     * @param bucketname   空间名
     * @param key        上传后名称
     * @param filePath      上传路径
     * @throws IOException
     */
    public Response upload(String bucketname,String key,String filePath) throws IOException{

        Response res  = null ;
        try {
            //调用put方法上传

            //设置callbackUrl以及callbackBody,七牛将文件名和文件大小回调给业务服务器
             res = uploadManager.put(filePath, null, auth.uploadToken(bucketname, null, 3600, new StringMap()
                    .put("callbackUrl", "http://your.domain.com/callback")
                    .put("callbackBody", "filename=$(fname)&filesize=$(fsize)")));
            //打印返回的信息
           // System.out.println(res.bodyString());

        } catch (QiniuException e) {
           // Response r = e.response;
            e.printStackTrace();
        }
        return res ;

    }


}
