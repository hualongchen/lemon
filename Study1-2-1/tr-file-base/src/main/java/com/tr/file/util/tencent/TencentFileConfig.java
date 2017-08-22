package com.tr.file.util.tencent;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Data
public class TencentFileConfig {


    @Value("${tencent.appId}")
    private long appId;

    @Value("${tencent.secretId}")
    private String secretId;

    @Value("${tencnet.secretKey}")
    private String secretKey;




}
