package com.lemon.chen.lemon.weixin.util;

import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.X509TrustManager;

/**
 * 
 * ClassName: MyX509TrustManager 
 * @Description: 信任管理器
 * @author chenhualong
 * @date 2014-9-14
 */
public class MyX509TrustUtil  implements X509TrustManager{
	
	
	// 检查客户端证书
	public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
	}

	// 检查服务器端证书
	public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
	}

	// 返回受信任的X509证书数组
	public X509Certificate[] getAcceptedIssuers() {
		return null;
	}

}
