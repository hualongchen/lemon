package com.tr.file.util.tencent;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.request.UploadFileRequest;
import com.qcloud.cos.sign.Credentials;
import lombok.Data;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;


@Data
public class TencentFileUtil {




    /**
     * 上传腾讯云文件
     *
     * @return
     */

    @SneakyThrows
    public static String uploadFile(String bucketName,String cosFilePath,String localFilePath,long appId,String secretId,String secretKey) {


        ClientConfig clientConfig = new ClientConfig();
        clientConfig.setRegion("cd");
        Credentials cred = new Credentials(appId,secretId,secretKey);

        COSClient   cosClient = new COSClient(clientConfig, cred);

        UploadFileRequest uploadFileRequest = new UploadFileRequest(bucketName, cosFilePath, localFilePath);

        uploadFileRequest.setEnableShaDigest(false);

        String uploadFileRet = cosClient.uploadFile(uploadFileRequest);


        return uploadFileRet ;

    }

}
