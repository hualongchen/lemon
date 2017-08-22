package com.tr.file.util.tencent;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.sign.Credentials;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TencentFileUtil {


    @Autowired
    private static TencentFileConfig tencentFileConfig;


    /**
     * 建造一次客户端请求连接
     *
     * @return
     */

    @SneakyThrows
    public static COSClient getClient() {

        /**
         华南	gz  华北	     tj    华东	sh
         西南	cd  新加坡	sgp
         *
         */
        ClientConfig clientConfig = new ClientConfig();
        clientConfig.setRegion("cd");
        Credentials cred = new Credentials(tencentFileConfig.getAppId(),
                tencentFileConfig.getSecretId(),
                tencentFileConfig.getSecretKey());

        return new COSClient(clientConfig, cred);

    }

    /**
     * 关闭客户端连接
     *
     * @param cosClient
     */
    @SneakyThrows
    public static void closeClient(COSClient cosClient) {

        cosClient.shutdown();
    }



}
