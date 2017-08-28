package com.lemon.chen.lemon.qiniu;

import java.io.IOException;

import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.Recorder;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.persistent.FileRecorder;
import com.qiniu.util.Auth;


/**
 * Created by chenhualong on 16/6/13.
 * 断点上传
 */
public class QiNiuBreakPointUploadUtil {



    //密钥配置
    Auth auth = Auth.create(QiNiuConstants.ACCESS_KEY, QiNiuConstants.SECRET_KEY);
    //创建上传对象
    UploadManager uploadManager = new UploadManager();


    /**
     *
     * @param bucketname   上传空间
     * @param key         上传后的名称
     * @param filePath    需要上传的文件地址
     * @param recordPath   记录断点的日志路径
     * @throws IOException
     */
    public void upload(String bucketname,String key,String filePath,String recordPath) throws IOException{
        //设置断点记录文件保存在指定文件夹或的File对象

        //实例化recorder对象
        Recorder recorder = new FileRecorder(recordPath);
        //实例化上传对象，并且传入一个recorder对象
        UploadManager uploadManager = new UploadManager(recorder);

        try {
            //调用put方法上传
            Response res = uploadManager.put("path/file", "key", auth.uploadToken(bucketname));

        } catch (QiniuException e) {
            Response r = e.response;
            // 请求失败时打印的异常的信息
            e.printStackTrace();

        }
    }


}
