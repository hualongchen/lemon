package com.lemon.chen.lemon.weixin.bean;

import java.io.Serializable;

public class Token implements Serializable {

	
	/**
	 * 微信token
	 */
	private static final long serialVersionUID = -4056874204825088295L;
	
	
	// 接口访问凭证
	private String accessToken;
	// 凭证有效期，单位：秒
	private int expiresIn;

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public int getExpiresIn() {
		return expiresIn;
	}

	public void setExpiresIn(int expiresIn) {
		this.expiresIn = expiresIn;
	}
	
}
