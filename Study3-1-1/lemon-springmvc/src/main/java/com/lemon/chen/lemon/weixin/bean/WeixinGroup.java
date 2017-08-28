package com.lemon.chen.lemon.weixin.bean;

import java.io.Serializable;

/**
 * 
 * ClassName: WeixinGroup 
 * @Description: 微信分组
 * @author chenhualong
 * @date 2014-9-14
 */
public class WeixinGroup implements Serializable{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2226549670823616531L;
	
	
		// 分组id
		private int id;
		// 分组名称
		private String name;
		// 分组内的用户数
		private int count;

		public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public int getCount() {
			return count;
		}

		public void setCount(int count) {
			this.count = count;
		}
}
