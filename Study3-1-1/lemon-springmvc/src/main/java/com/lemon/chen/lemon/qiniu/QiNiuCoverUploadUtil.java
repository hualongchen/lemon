package com.lemon.chen.lemon.qiniu;

import java.io.IOException;

import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import com.qiniu.util.StringMap;

/**
 * Created by chenhualong on 16/6/13.
 *
 * 覆盖上传
 */
public class QiNiuCoverUploadUtil {


    //密钥配置
    Auth auth = Auth.create(QiNiuConstants.ACCESS_KEY, QiNiuConstants.SECRET_KEY);
    //创建上传对象
    UploadManager uploadManager = new UploadManager();


    /**
     *
     * @param bucketname   空间名
     * @param key          上传后的名字
     * @param filePath      本地上传路径
     * @throws IOException
     */
    public void upload(String bucketname,String key,String filePath) throws IOException{
        try {
            //调用put方法上传，这里指定的key和上传策略中的key要一致
            //<bucket>:<key>，表示只允许用户上传指定key的文件。在这种格式下文件默认允许“修改”，已存在同名资源则会被本次覆盖。
            //如果希望只能上传指定key的文件，并且不允许修改，那么可以将下面的 insertOnly 属性值设为 1。
            //第三个参数是token的过期时间
            Response res = uploadManager.put(filePath, key,auth.uploadToken(bucketname, key, 3600, new StringMap().put("insertOnly", 1 )) );


        } catch (QiniuException e) {
            Response r = e.response;
            e.printStackTrace();
        }
    }



}
