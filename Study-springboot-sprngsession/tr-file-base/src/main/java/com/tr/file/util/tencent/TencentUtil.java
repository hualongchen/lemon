package com.tr.file.util.tencent;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;

import com.qcloud.cos.request.*;
import com.qcloud.cos.sign.Credentials;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;


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
     * 返回值:
     * {
     "code": 0,
     "message": "SUCCESS",
     "request_id": "NTk5Y2UxYWRfNWJiMjU4NjRfODVlM18yNjBkZg==",
     "data": {
     "access_url": "http://compact-1253380732.file.myqcloud.com/test.jpg",
     "resource_path": "/1253380732/compact/test.jpg",
     "source_url": "http://compact-1253380732.coscd.myqcloud.com/test.jpg",
     "url": "http://cd.file.myqcloud.com/files/v2/1253380732/compact/test.jpg",
     "vid": "a5655ad35e1db0dcab157fd5ff5563b31503453614"
     }
     }
     */

    /**
     * SecretId: AKIDvjO0XxhLMBrLeqtuqfIj5EC7xKXbCing
     SecretKey: sa7TeiDcrvw7A1lMm1HsU8kPuG4tX3NZ
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        long appId = 1253380732;
        String secretId = "AKID795bWioYpxthNrAyPjtv6g9a8lcXiynW";
        String secretKey = "lShttxulYyJd1ITWO6Wr9GF8pKgyK524";
        String bucketName = "compact";
        ClientConfig clientConfig = new ClientConfig();
        clientConfig.setRegion("cd");
        Credentials cred = new Credentials(appId, secretId, secretKey);
        COSClient cosClient = new COSClient(clientConfig, cred);
        /**
         * 生成客户端了
         */

        //byte[] contentBuffer = TencentUtil.fileToBetyArray("/Users/chenhualong/Documents/dev_tool/tt.jpg");


        /**
         * 只能是字母或则数字
         */
        String cosFilePath = "/ttff.docx";
        String localFilePath1 = "/Users/chenhualong/Desktop/10010.docx";
        //UploadFileRequest uploadFileRequest = new UploadFileRequest(bucketName, cosFilePath, contentBuffer);
        UploadFileRequest uploadFileRequest = new UploadFileRequest(bucketName, cosFilePath, localFilePath1);
        uploadFileRequest.setEnableShaDigest(false);
        String uploadFileRet = cosClient.uploadFile(uploadFileRequest);
        System.out.println("upload file ret:" + uploadFileRet);


        /**
         * 关闭
         */
        cosClient.shutdown();
    }

    /**
     *  文件转流
     * @param filePath
     * @return
     */
    public static byte[] fileToBetyArray(String filePath)
    {
        FileInputStream fileInputStream = null;
        File file = new File(filePath);
        byte[] bFile = null;
        try {
            bFile = new byte[(int) file.length()];
            fileInputStream = new FileInputStream(file);
            fileInputStream.read(bFile);
            fileInputStream.close();
            System.out.println("Done");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fileInputStream.close();
                bFile.clone();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return bFile;
    }



}
