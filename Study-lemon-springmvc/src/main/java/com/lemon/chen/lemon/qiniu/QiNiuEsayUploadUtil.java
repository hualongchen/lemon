package com.lemon.chen.lemon.qiniu;

import com.qiniu.util.Auth;
import java.io.IOException;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.UploadManager;

/**
 * Created by chenhualong on 16/6/13.
 * 一般上传
 */
public class QiNiuEsayUploadUtil {




    //密钥配置
    Auth auth = Auth.create(QiNiuConstants.ACCESS_KEY, QiNiuConstants.SECRET_KEY);
    //创建上传对象
    UploadManager uploadManager = new UploadManager();


    /**
     * 最终上传  接口
     * @param bucketname   上传的空间
     * @param filePath     本地要上传的路径
     * @param key         上传后文件的名称
     * @throws IOException
     */
    public void upload(String bucketname,String filePath,String key) throws IOException{
        try {
            //调用put方法上传
             uploadManager.put(filePath, key, auth.uploadToken(bucketname));

        } catch (QiniuException e) {
            Response r = e.response;
            // 请求失败时打印的异常的信息
              e.printStackTrace();

        }
    }





}
