package com.lemon.chen.lemon.weixin.bean;

import java.io.Serializable;

/**
 * 
 * ClassName: WeixinQRCode 
 * @Description: 微信二维码
 * @author chenhualong
 * @date 2015-6-14
 */
public class WeixinQRCode implements Serializable{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -9218451686111768036L;
	
		// 获取的二维码ticket
		private String ticket;
		
		// 二维码的有效时间，单位为秒，最大不超过1800s  也就是半个小时
		private int expireSeconds;
		
		//二维码地址
		private String url ;
		
		

		public String getUrl() {
			return url;
		}

		public void setUrl(String url) {
			this.url = url;
		}

		public String getTicket() {
			return ticket;
		}

		public void setTicket(String ticket) {
			this.ticket = ticket;
		}

		public int getExpireSeconds() {
			return expireSeconds;
		}

		public void setExpireSeconds(int expireSeconds) {
			this.expireSeconds = expireSeconds;
		}
}
