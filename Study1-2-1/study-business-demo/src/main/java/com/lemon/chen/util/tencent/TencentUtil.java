package com.lemon.chen.util.tencent;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;

import com.qcloud.cos.request.*;
import com.qcloud.cos.sign.Credentials;


public class TencentUtil {


    /**
     * 地域	地域简称	默认下载域名	上传域名
     华南	gz	<bucketname>-<APPID>.cosgz.myqcloud.com	gz.file.myqcloud.com
     华北	tj	<bucketname>-<APPID>.costj.myqcloud.com	tj.file.myqcloud.com
     华东	sh	<bucketname>-<APPID>.cossh.myqcloud.com	sh.file.myqcloud.com
     西南	cd	<bucketname>-<APPID>.coscd.myqcloud.com	cd.file.myqcloud.com
     新加坡	sgp	<bucketname>-<APPID>.cossgp.myqcloud.com	sgp.file.myqcloud.com
     * @param args
     * @throws Exception
     */


    /**
     * SecretId: AKIDvjO0XxhLMBrLeqtuqfIj5EC7xKXbCing
     SecretKey: sa7TeiDcrvw7A1lMm1HsU8kPuG4tX3NZ
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        long appId = 1253692788;
        String secretId = "AKID277wa4IUSFrIXaoCrLiwzqaDez98iGUY";
        String secretKey = "73oOm2723Xr11pSumZFk4C8LUTHtJi5I";
        String bucketName = "compact";
        ClientConfig clientConfig = new ClientConfig();
        clientConfig.setRegion("cd");
        Credentials cred = new Credentials(appId, secretId, secretKey);
        COSClient cosClient = new COSClient(clientConfig, cred);
        /**
         * 生成客户端了
         */

        /**
         * 只能是字母或则数字
         */
        String cosFilePath = "/test.jpg";
        String localFilePath1 = "/Users/chenhualong/Documents/dev_tool/tt.jpg";
        UploadFileRequest uploadFileRequest = new UploadFileRequest(bucketName, cosFilePath, localFilePath1);
        uploadFileRequest.setEnableShaDigest(false);
        String uploadFileRet = cosClient.uploadFile(uploadFileRequest);
        System.out.println("upload file ret:" + uploadFileRet);


        /**
         * 关闭
         */
        cosClient.shutdown();
    }
}
