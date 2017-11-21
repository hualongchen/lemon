package com.lemon.chen.lemon.weixin.util;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

import javax.net.ssl.HttpsURLConnection;

import com.lemon.chen.lemon.weixin.bean.*;
import net.sf.json.JSONArray;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;




/**
 * 微信api公用类
 * @author chenhualong
 *
 */
public class WeixinAPIUtil {

	private static final Logger log = LoggerFactory.getLogger(WeixinAPIUtil.class);
	
	
	/**
	 * 获取用户信息
	 * 
	 * @param accessToken 接口访问凭证
	 * @param openId 用户标识
	 * @return WeixinUserInfo    httpsRequestForTimeOutForTimeOut
	 */
	public static WeixinUserInfo getUserInfo(String accessToken, String openId, int outTime) {
		
		WeixinUserInfo weixinUserInfo = null;
		// 拼接请求地址
		String requestUrl = "https://api.weixin.qq.com/cgi-bin/user/info?access_token=ACCESS_TOKEN&openid=OPENID";
		requestUrl = requestUrl.replace("ACCESS_TOKEN", accessToken).replace("OPENID", openId);
		// 获取用户信息
		JSONObject jsonObject =  HttpUtil.httpsRequestForTimeOut(requestUrl, "GET", null,outTime);

		if (null != jsonObject) {
			try {
				weixinUserInfo = new WeixinUserInfo();
				
				// 用户的标识
				weixinUserInfo.setOpenId(jsonObject.getString("openid"));
				// 关注状态（1是关注，0是未关注），未关注时获取不到其余信息
				weixinUserInfo.setSubscribe(jsonObject.getInt("subscribe"));
				
				//判断是否关注是有数据
				if(jsonObject.getInt("subscribe")==1){
					
					// 用户关注时间
					weixinUserInfo.setSubscribeTime(jsonObject.getString("subscribe_time"));
					// 昵称
					weixinUserInfo.setNickname(jsonObject.getString("nickname"));
					// 用户的性别（1是男性，2是女性，0是未知）
					weixinUserInfo.setSex(jsonObject.getInt("sex"));
					// 用户所在国家
					weixinUserInfo.setCountry(jsonObject.getString("country"));
					// 用户所在省份
					weixinUserInfo.setProvince(jsonObject.getString("province"));
					// 用户所在城市
					weixinUserInfo.setCity(jsonObject.getString("city"));
					// 用户的语言，简体中文为zh_CN
					weixinUserInfo.setLanguage(jsonObject.getString("language"));
					// 用户头像
					weixinUserInfo.setHeadImgUrl(jsonObject.getString("headimgurl"));
				}
			} catch (Exception e) {
				
					int errorCode = jsonObject.getInt("errcode");
					String errorMsg = jsonObject.getString("errmsg");
					log.error("com.cxj.home.util.weixin.getUserInfo: msg from weixin error ,errorCode is:"+errorCode+", errorMsg is :"+errorMsg);
				
			}
		}
		return weixinUserInfo;
	}
	
	
	/**
	 * 创建临时带参二维码
	 * 
	 * @param accessToken 接口访问凭证
	 * @param expireSeconds 二维码有效时间，单位为秒，最大不超过1800
	 * @param sceneId 场景ID
	 * @return WeixinQRCode
	 */
	public static WeixinQRCode createTemporaryQRCode(String accessToken, int expireSeconds, int sceneId, int outTime) {
		
		WeixinQRCode weixinQRCode = null;
		// 拼接请求地址
		String requestUrl = "https://api.weixin.qq.com/cgi-bin/qrcode/create?access_token=ACCESS_TOKEN";
		requestUrl = requestUrl.replace("ACCESS_TOKEN", accessToken);
		// 需要提交的json数据
		String jsonMsg = "{\"expire_seconds\": %d, \"action_name\": \"QR_SCENE\", \"action_info\": {\"scene\": {\"scene_id\": %d}}}";
		// 创建临时带参二维码
		JSONObject jsonObject =  HttpUtil.httpsRequestForTimeOut(requestUrl, "POST", String.format(jsonMsg, expireSeconds, sceneId),outTime);

		if (null != jsonObject) {
			try {
				weixinQRCode = new WeixinQRCode();
				weixinQRCode.setTicket(jsonObject.getString("ticket"));
				weixinQRCode.setExpireSeconds(jsonObject.getInt("expire_seconds"));
			} catch (Exception e) {
				weixinQRCode = null;
				int errorCode = jsonObject.getInt("errcode");
				String errorMsg = jsonObject.getString("errmsg");
				log.error("com.cxj.home.util.weixin.createTemporaryQRCode: msg from weixin error ,errorCode is:"+errorCode+", errorMsg is :"+errorMsg);
			}
		}
		return weixinQRCode;
	}

	/**
	 * 创建永久带参二维码
	 * 
	 * @param accessToken 接口访问凭证
	 * @param sceneId 场景ID
	 * @return ticket
	 */
	public static WeixinQRCode createPermanentQRCode(String accessToken, String sceneId,int outTime) {
		
		WeixinQRCode  code = new WeixinQRCode();
		// 拼接请求地址
		String requestUrl = "https://api.weixin.qq.com/cgi-bin/qrcode/create?access_token=ACCESS_TOKEN";
		requestUrl = requestUrl.replace("ACCESS_TOKEN", accessToken);
		// 需要提交的json数据
		String jsonMsg = "{\"action_name\": \"QR_LIMIT_STR_SCENE\", \"action_info\": {\"scene\": {\"scene_id\": %d}}}";
		// 创建永久带参二维码
		JSONObject jsonObject =  HttpUtil.httpsRequestForTimeOut(requestUrl, "POST", String.format(jsonMsg, sceneId),outTime);

		if (null != jsonObject) {
			try {
				code.setTicket(jsonObject.getString("ticket"));
				code.setUrl(jsonObject.getString("url"));
			} catch (Exception e) {
				int errorCode = jsonObject.getInt("errcode");
				String errorMsg = jsonObject.getString("errmsg");
				log.error("com.cxj.home.util.weixin.createPermanentQRCode: msg from weixin error ,errorCode is:"+errorCode+", errorMsg is :"+errorMsg);
			}
		}
		return code;
	}

	/**
	 * 根据ticket换取二维码
	 * 
	 * @param ticket 二维码ticket
	 * @param savePath 保存路径, 可以改造一下
	 */
	public static String getQRCode(String ticket, String savePath) {
		String filePath = null;
		// 拼接请求地址
		String requestUrl = "https://mp.weixin.qq.com/cgi-bin/showqrcode?ticket=TICKET";
		requestUrl = requestUrl.replace("TICKET",  HttpUtil.urlEncodeUTF8(ticket));
		try {
			URL url = new URL(requestUrl);
			HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
			conn.setDoInput(true);
			conn.setRequestMethod("GET");

			if (!savePath.endsWith("/")) {
				savePath += "/";
			}
			// 将ticket作为文件名
			filePath = savePath + ticket + ".jpg";

			// 将微信服务器返回的输入流写入文件
			BufferedInputStream bis = new BufferedInputStream(conn.getInputStream());
			FileOutputStream fos = new FileOutputStream(new File(filePath));
			byte[] buf = new byte[8096];
			int size = 0;
			while ((size = bis.read(buf)) != -1)
				fos.write(buf, 0, size);
			fos.close();
			bis.close();

			conn.disconnect();
		} catch (Exception e) {
			e.printStackTrace();
			filePath = null;
			log.error("com.cxj.home.util.weixin.getQRCode: msg from weixin error ");
		}
		return filePath;
	}
	
	
	/**
	 * 获取关注者列表
	 * 
	 * @param accessToken 调用接口凭证
	 * @param nextOpenId 第一个拉取的openId，不填默认从头开始拉取
	 * @return WeixinUserList
	 */
	@SuppressWarnings( { "unchecked", "deprecation" })
	public static WeixinUserList getUserList(String accessToken, String nextOpenId, int outTime) {
		
		WeixinUserList weixinUserList = null;

		if (null == nextOpenId)
			nextOpenId = "";

		// 拼接请求地址
		String requestUrl = "https://api.weixin.qq.com/cgi-bin/user/get?access_token=ACCESS_TOKEN&next_openid=NEXT_OPENID";
		requestUrl = requestUrl.replace("ACCESS_TOKEN", accessToken).replace("NEXT_OPENID", nextOpenId);
		// 获取关注者列表
		JSONObject jsonObject =  HttpUtil.httpsRequestForTimeOut(requestUrl, "GET", null,outTime);
		// 如果请求成功
		if (null != jsonObject) {
			try {
				weixinUserList = new WeixinUserList();
				weixinUserList.setTotal(jsonObject.getInt("total"));
				weixinUserList.setCount(jsonObject.getInt("count"));
				weixinUserList.setNextOpenId(jsonObject.getString("next_openid"));
				JSONObject dataObject = (JSONObject) jsonObject.get("data");
				weixinUserList.setOpenIdList(JSONArray.toList(dataObject.getJSONArray("openid"), List.class));
			} catch (JSONException e) {
				weixinUserList = null;
				int errorCode = jsonObject.getInt("errcode");
				String errorMsg = jsonObject.getString("errmsg");
				log.error("com.cxj.home.util.weixin.getUserList: msg from weixin error ,errorCode is:"+errorCode+", errorMsg is :"+errorMsg);
			}
		}
		return weixinUserList;
	}
	
	
	/**
	 * 查询分组
	 * 
	 * @param accessToken 调用接口凭证
	 */
	@SuppressWarnings( { "unchecked", "deprecation" })
	public static List<WeixinGroup> getGroups(String accessToken, int outTime) {
		List<WeixinGroup> weixinGroupList = null;
		// 拼接请求地址
		String requestUrl = "https://api.weixin.qq.com/cgi-bin/groups/get?access_token=ACCESS_TOKEN";
		requestUrl = requestUrl.replace("ACCESS_TOKEN", accessToken);
		// 查询分组
		JSONObject jsonObject =  HttpUtil.httpsRequestForTimeOut(requestUrl, "GET", null,outTime);

		if (null != jsonObject) {
			try {
				weixinGroupList = JSONArray.toList(jsonObject.getJSONArray("groups"), WeixinGroup.class);
			} catch (JSONException e) {
				weixinGroupList = null;
				int errorCode = jsonObject.getInt("errcode");
				String errorMsg = jsonObject.getString("errmsg");
				log.error("com.cxj.home.util.weixin.getGroups: msg from weixin error ,errorCode is:"+errorCode+", errorMsg is :"+errorMsg);
			}
		}
		return weixinGroupList;
	}

	/**
	 * 创建分组
	 * 
	 * @param accessToken 接口访问凭证
	 * @param groupName 分组名称
	 * @return
	 */
	public static WeixinGroup createGroup(String accessToken, String groupName,int outTime) {
		WeixinGroup weixinGroup = null;
		// 拼接请求地址
		String requestUrl = "https://api.weixin.qq.com/cgi-bin/groups/create?access_token=ACCESS_TOKEN";
		requestUrl = requestUrl.replace("ACCESS_TOKEN", accessToken);
		// 需要提交的json数据
		String jsonData = "{\"group\" : {\"name\" : \"%s\"}}";
		// 创建分组
		JSONObject jsonObject =  HttpUtil.httpsRequestForTimeOut(requestUrl, "POST", String.format(jsonData, groupName),outTime);

		if (null != jsonObject) {
			try {
				weixinGroup = new WeixinGroup();
				weixinGroup.setId(jsonObject.getJSONObject("group").getInt("id"));
				weixinGroup.setName(jsonObject.getJSONObject("group").getString("name"));
			} catch (JSONException e) {
				weixinGroup = null;
				int errorCode = jsonObject.getInt("errcode");
				String errorMsg = jsonObject.getString("errmsg");
				log.error("com.cxj.home.util.weixin.createGroup: msg from weixin error ,errorCode is:"+errorCode+", errorMsg is :"+errorMsg);
			}
		}
		return weixinGroup;
	}

	/**
	 * 修改分组名
	 * 
	 * @param accessToken 接口访问凭证
	 * @param groupId 分组id
	 * @param groupName 修改后的分组名
	 * @return true | false
	 */
	public static boolean updateGroup(String accessToken, int groupId, String groupName,int outTime) {
		boolean result = false;
		// 拼接请求地址
		String requestUrl = "https://api.weixin.qq.com/cgi-bin/groups/update?access_token=ACCESS_TOKEN";
		requestUrl = requestUrl.replace("ACCESS_TOKEN", accessToken);
		// 需要提交的json数据
		String jsonData = "{\"group\": {\"id\": %d, \"name\": \"%s\"}}";
		// 修改分组名
		JSONObject jsonObject = HttpUtil.httpsRequestForTimeOut(requestUrl, "POST", String.format(jsonData, groupId, groupName),outTime);

		if (null != jsonObject) {
			int errorCode = jsonObject.getInt("errcode");
			String errorMsg = jsonObject.getString("errmsg");
			
			if (0 == errorCode) {
				result = true;
			}else{
				log.error("com.cxj.home.util.weixin.updateGroup: msg from weixin error ,errorCode is:"+errorCode+", errorMsg is :"+errorMsg);
			} 
		}
		return result;
	}

	/**
	 * 移动用户分组
	 * 
	 * @param accessToken 接口访问凭证
	 * @param openId 用户标识
	 * @param groupId 分组id
	 * @return true | false
	 */
	public static boolean updateMemberGroup(String accessToken, String openId, int groupId,int outTime) {
		boolean result = false;
		// 拼接请求地址
		String requestUrl = "https://api.weixin.qq.com/cgi-bin/groups/members/update?access_token=ACCESS_TOKEN";
		requestUrl = requestUrl.replace("ACCESS_TOKEN", accessToken);
		// 需要提交的json数据
		String jsonData = "{\"openid\":\"%s\",\"to_groupid\":%d}";
		// 移动用户分组
		JSONObject jsonObject =  HttpUtil.httpsRequestForTimeOut(requestUrl, "POST", String.format(jsonData, openId, groupId),outTime);

		if (null != jsonObject) {
			int errorCode = jsonObject.getInt("errcode");
			String errorMsg = jsonObject.getString("errmsg");
			
			if (0 == errorCode) {
				result = true;
			} else{
				log.error("com.cxj.home.util.weixin.updateMemberGroup: msg from weixin error ,errorCode is:"+errorCode+", errorMsg is :"+errorMsg);
			}
		}
		return result;
	}

	
    /**
	 * 通过网页授权获取用户信息
	 * 
	 * @param accessToken 网页授权接口调用凭证
	 * @param openId 用户标识
	 * @return SNSUserInfo
	 */
	@SuppressWarnings({ "unchecked", "deprecation" })
	public static SNSUserInfo getSNSUserInfo(String accessToken, String openId,int outTime) {
		SNSUserInfo snsUserInfo = null;
		
		String getuserInfo_url="https://api.weixin.qq.com/sns/userinfo?access_token=ACCESS_TOKEN&openid=OPENID";
		
		// 拼接请求地址
		String requestUrl = getuserInfo_url.replace("ACCESS_TOKEN", accessToken).replace("OPENID", openId);
		// 通过网页授权获取用户信息
		JSONObject jsonObject = HttpUtil.httpsRequestForTimeOut(requestUrl, "GET", null,outTime);

		if (null != jsonObject) {
			try {
				snsUserInfo = new SNSUserInfo();
				// 用户的标识
				snsUserInfo.setOpenId(jsonObject.getString("openid"));
				// 昵称
				snsUserInfo.setNickname(jsonObject.getString("nickname"));
				// 性别（1是男性，2是女性，0是未知）
				snsUserInfo.setSex(jsonObject.getInt("sex"));
				// 用户所在国家
				snsUserInfo.setCountry(jsonObject.getString("country"));
				// 用户所在省份
				snsUserInfo.setProvince(jsonObject.getString("province"));
				// 用户所在城市
				snsUserInfo.setCity(jsonObject.getString("city"));
				// 用户头像
				snsUserInfo.setHeadImgUrl(jsonObject.getString("headimgurl"));
				// 用户特权信息
				snsUserInfo.setPrivilegeList(JSONArray.toList(jsonObject.getJSONArray("privilege"), List.class));
			} catch (Exception e) {
				snsUserInfo = null;
				int errorCode = jsonObject.getInt("errcode");
				String errorMsg = jsonObject.getString("errmsg");
				log.error("com.cxj.home.util.weixin.getSNSUserInfo: msg from weixin error ,errorCode is:"+errorCode+", errorMsg is :"+errorMsg);
			}
		}
		return snsUserInfo;
	}
	
    
    /**
	 * 刷新网页授权凭证
	 * 
	 * @param appId 公众账号的唯一标识
	 * @param refreshToken
	 * @return WeixinAouth2Token
	 */
	public static WeixinOauth2Token refreshOauth2AccessToken(String appId, String refreshToken, int outTime) {
		WeixinOauth2Token wat = null;
		// 拼接请求地址
		String refeshAauth_url="https://api.weixin.qq.com/sns/oauth2/refresh_token?appid=APPID&grant_type=refresh_token&refresh_token=REFRESH_TOKEN";
		String requestUrl = refeshAauth_url.replace("APPID", appId);
		requestUrl = requestUrl.replace("REFRESH_TOKEN", refreshToken);
		// 刷新网页授权凭证
		JSONObject jsonObject = HttpUtil.httpsRequestForTimeOut(requestUrl, "GET", null,outTime);
		if (null != jsonObject) {
			try {
				wat = new WeixinOauth2Token();
				wat.setAccessToken(jsonObject.getString("access_token"));
				wat.setExpiresIn(jsonObject.getInt("expires_in"));
				wat.setRefreshToken(jsonObject.getString("refresh_token"));
				wat.setOpenId(jsonObject.getString("openid"));
				wat.setScope(jsonObject.getString("scope"));
			} catch (Exception e) {
				wat = null;
				int errorCode = jsonObject.getInt("errcode");
				String errorMsg = jsonObject.getString("errmsg");
				log.error("com.cxj.home.util.weixin.refreshOauth2AccessToken: msg from weixin error ,errorCode is:"+errorCode+", errorMsg is :"+errorMsg);
			}
		}
		return wat;
	}
	
	
	/**
	 * 获取网页授权凭证
	 * 
	 * @param appId 公众账号的唯一标识
	 * @param appSecret 公众账号的密钥
	 * @param code
	 * @return WeixinAouth2Token
	 */
	public static WeixinOauth2Token getOauth2AccessToken(String appId, String appSecret, String code,int outTime) {
		WeixinOauth2Token wat = null;
		// 拼接请求地址
		String getOauth_url="https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code";
		String requestUrl = getOauth_url.replace("APPID", appId);
		requestUrl = requestUrl.replace("SECRET", appSecret);
		requestUrl = requestUrl.replace("CODE", code);
		// 获取网页授权凭证
		JSONObject jsonObject = HttpUtil.httpsRequestForTimeOut(requestUrl, "GET", null,outTime);
		if (null != jsonObject) {
			try {
				wat = new WeixinOauth2Token();
				wat.setAccessToken(jsonObject.getString("access_token"));
				wat.setExpiresIn(jsonObject.getInt("expires_in"));
				wat.setRefreshToken(jsonObject.getString("refresh_token"));
				wat.setOpenId(jsonObject.getString("openid"));
				wat.setScope(jsonObject.getString("scope"));
			} catch (Exception e) {
				wat = null;
				int errorCode = jsonObject.getInt("errcode");
				String errorMsg = jsonObject.getString("errmsg");
				log.error("com.cxj.home.util.weixin.getOauth2AccessToken: msg from weixin error ,errorCode is:"+errorCode+", errorMsg is :"+errorMsg);
			}
		}
		return wat;
	}
    
    
    /**
     * 设置行业id
     * @param industry_id1
     * @param industry_id2
     * @param accessToken
     */
    public static void setIndustry(String industry_id1,String industry_id2,String accessToken){
    	
    	//这个直接调试工具，就可以完成的哈
    	
    }
    
    /**
	 * 下载媒体文件
	 * 
	 * @param accessToken 接口访问凭证
	 * @param mediaId 媒体文件标识
	 * @param savePath 文件在服务器上的存储路径
	 * @return
	 */
	public static String getMedia(String accessToken, String mediaId, String savePath) {
		String filePath = null;
		
		String download_url="http://file.api.weixin.qq.com/cgi-bin/media/get?access_token=ACCESS_TOKEN&media_id=MEDIA_ID";
		
		// 拼接请求地址
		String requestUrl = download_url.replace("ACCESS_TOKEN", accessToken).replace("MEDIA_ID", mediaId);
		try {
			URL url = new URL(requestUrl);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoInput(true);
			conn.setRequestMethod("GET");

			if (!savePath.endsWith("/")) {
				savePath += "/";
			}
			// 根据内容类型获取扩展名
			String fileExt = getFileExt(conn.getHeaderField("Content-Type"));
			// 将mediaId作为文件名
			filePath = savePath + mediaId + fileExt;

			BufferedInputStream bis = new BufferedInputStream(conn.getInputStream());
			FileOutputStream fos = new FileOutputStream(new File(filePath));
			byte[] buf = new byte[8096];
			int size = 0;
			while ((size = bis.read(buf)) != -1)
				fos.write(buf, 0, size);
			fos.close();
			bis.close();

			conn.disconnect();
			
		} catch (Exception e) {
			filePath = null;
			e.printStackTrace();
			log.error("com.cxj.home.util.weixin.getMedia: msg from weixin error ");
		}
		return filePath;
	}
    
    /**
	 * 上传媒体文件
	 * 
	 * @param accessToken 接口访问凭证
	 * @param type 媒体文件类型（image、voice、video和thumb）
	 * @param mediaFileUrl 媒体文件的url
	 */
	public static WeixinMedia uploadMedia(String accessToken, String type, String mediaFileUrl) {
		WeixinMedia weixinMedia = null;
		
		String upload_url="http://file.api.weixin.qq.com/cgi-bin/media/upload?access_token=ACCESS_TOKEN&type=TYPE";
		// 拼装请求地址
		String uploadMediaUrl = upload_url.replace("ACCESS_TOKEN", accessToken).replace("TYPE", type);

		// 定义数据分隔符
		String boundary = "------------7da2e536604c8";
		try {
			URL uploadUrl = new URL(uploadMediaUrl);
			HttpURLConnection uploadConn = (HttpURLConnection) uploadUrl.openConnection();
			uploadConn.setDoOutput(true);
			uploadConn.setDoInput(true);
			uploadConn.setRequestMethod("POST");
			// 设置请求头Content-Type
			uploadConn.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
			// 获取媒体文件上传的输出流（往微信服务器写数据）
			OutputStream outputStream = uploadConn.getOutputStream();

			URL mediaUrl = new URL(mediaFileUrl);
			HttpURLConnection meidaConn = (HttpURLConnection) mediaUrl.openConnection();
			meidaConn.setDoOutput(true);
			meidaConn.setRequestMethod("GET");

			// 从请求头中获取内容类型
			String contentType = meidaConn.getHeaderField("Content-Type");
			// 根据内容类型判断文件扩展名
			String fileExt = getFileExt(contentType);
			// 请求体开始
			outputStream.write(("--" + boundary + "\r\n").getBytes());
			outputStream.write(String.format("Content-Disposition: form-data; name=\"media\"; filename=\"file1%s\"\r\n", fileExt).getBytes());
			outputStream.write(String.format("Content-Type: %s\r\n\r\n", contentType).getBytes());

			// 获取媒体文件的输入流（读取文件）
			BufferedInputStream bis = new BufferedInputStream(meidaConn.getInputStream());
			byte[] buf = new byte[8096];
			int size = 0;
			while ((size = bis.read(buf)) != -1) {
				// 将媒体文件写到输出流（往微信服务器写数据）
				outputStream.write(buf, 0, size);
			}
			// 请求体结束
			outputStream.write(("\r\n--" + boundary + "--\r\n").getBytes());
			outputStream.close();
			bis.close();
			meidaConn.disconnect();

			// 获取媒体文件上传的输入流（从微信服务器读数据）
			InputStream inputStream = uploadConn.getInputStream();
			InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
			BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
			StringBuffer buffer = new StringBuffer();
			String str = null;
			while ((str = bufferedReader.readLine()) != null) {
				buffer.append(str);
			}
			bufferedReader.close();
			inputStreamReader.close();
			// 释放资源
			inputStream.close();
			inputStream = null;
			uploadConn.disconnect();

			// 使用JSON-lib解析返回结果
			JSONObject jsonObject = JSONObject.fromObject(buffer.toString());
			weixinMedia = new WeixinMedia();
			weixinMedia.setType(jsonObject.getString("type"));
			// type等于thumb时的返回结果和其它类型不一样
			if ("thumb".equals(type))
				weixinMedia.setMediaId(jsonObject.getString("thumb_media_id"));
			else
				weixinMedia.setMediaId(jsonObject.getString("media_id"));
			    weixinMedia.setCreatedAt(jsonObject.getInt("created_at"));
		} catch (Exception e) {
			weixinMedia = null;
			e.printStackTrace();
			log.error("com.cxj.home.util.weixin.uploadMedia: msg from weixin error ");
		}
		return weixinMedia;
	}
    /**
     * 获取微信的Ip列表
     * @param accessToken 凭证
     * @return  ip列表
     */
    public static List<WeixinIp>  getWeixinIp(String accessToken,int outTime){
    	
    	List<WeixinIp>  ipList = null ;
    	
    	String ipList_url="https://api.weixin.qq.com/cgi-bin/getcallbackip?access_token=ACCESS_TOKEN";
    	
    	String requestUrl= ipList_url.replace("ACCESS_TOKEN", accessToken);   	
    	// 发起GET请求获取凭证
		JSONObject jsonObject = HttpUtil.httpsRequestForTimeOut(requestUrl, "GET", null,outTime); 	
		if (null != jsonObject) {
			try {
				ipList = new ArrayList<WeixinIp>();
				JSONArray  array = jsonObject.getJSONArray("ip_list");
				for (int i = 0; i < array.size(); i++) {
					WeixinIp  ip = new WeixinIp();
					
					ip.setIp(array.getString(i));
					
					ipList.add(ip);
				}
				
			} catch (JSONException e) {
				ipList = null;
				log.error("com.cxj.home.util.weixin.getWeixinIp: msg from weixin error.");
			}
		}
    	
    	
    	
    	return ipList ;
    }

	/**
	 * 获取接口访问凭证
	 * 
	 * @param appid 凭证
	 * @param appsecret 密钥
	 * @return token  凭证asstoken
	 */
	public static Token getToken(String appid, String appsecret,int outTime) {
		Token token = null;
		
		String token_url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";
		
		String requestUrl = token_url.replace("APPID", appid).replace("APPSECRET", appsecret);
		// 发起GET请求获取凭证
		JSONObject jsonObject = HttpUtil.httpsRequestForTimeOut(requestUrl, "GET", null,outTime);

		if (null != jsonObject) {
			try {
				token = new Token();
				token.setAccessToken(jsonObject.getString("access_token"));
				token.setExpiresIn(jsonObject.getInt("expires_in"));
			} catch (JSONException e) {
				token = null;
				log.error("com.cxj.home.util.weixin.getToken: msg from weixin error.");
			}
		}
		return token;
	}


	/**
	 * 
	* @Title: getTicket 
	* @Description: 微信获取ticket
	* @param @param accessToken
	* @param @return   
	* @return String
	* @throws
	 */
	public static String getTicket(String accessToken,int outTime){
		
		String ticket_url = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=ACCESS_TOKEN&type=jsapi";
		String requestUrl = ticket_url.replace("ACCESS_TOKEN", accessToken);
		String ticket="";
		JSONObject jsonObject =null ;
		try {
			 jsonObject = HttpUtil.httpsRequestForTimeOut(requestUrl, "GET", null,outTime);

			 int errorCode=jsonObject.getInt("errcode");
			 String errorMsg=jsonObject.getString("errmsg");
				
			if(errorCode==0){
				
				ticket=jsonObject.getString("ticket");
			}else{
				log.error("com.cxj.home.util.weixin.getTicket: msg from weixin error ,errorCode is:"+errorCode+", errorMsg is :"+errorMsg);
			}
		} catch (Exception e) {
			int errorCode=jsonObject.getInt("errcode");
			String errorMsg=jsonObject.getString("errmsg");
			log.error("com.cxj.home.util.weixin.getTicket: msg from weixin error ,errorCode is:"+errorCode+", errorMsg is :"+errorMsg);
			e.printStackTrace();
		}
		return ticket ;
	}
	/**
	 * 
	* @Title: sign 
	* @Description: 获取js的sdk权限
	* @param @param jsapi_ticket
	* @param @param url
	* @param @return   
	* @return Map<String,String>
	* @throws
	 */
	public static Map<String, String> signature( String url,String weixinId, String weixinSecret,int outTime) {
        Map<String, String> ret = new HashMap<String, String>();
        String nonce_str = create_nonce_str();
        String timestamp = create_timestamp();
        String string1;
        String signature = "";

        String jsapi_ticket=getTicket(getToken(weixinId,weixinSecret,outTime).getAccessToken(),outTime);
        //注意这里参数名必须全部小写，且必须有序
        string1 = "jsapi_ticket=" + jsapi_ticket +
                "&noncestr=" + nonce_str +
                "&timestamp=" + timestamp +
                "&url=" + url;
        try
        {
            MessageDigest crypt = MessageDigest.getInstance("SHA-1");
            crypt.reset();
            crypt.update(string1.getBytes("UTF-8"));
            signature = byteToHex(crypt.digest());
            
            
        }
        catch (NoSuchAlgorithmException e)
        {
            e.printStackTrace();
        }
        catch (UnsupportedEncodingException e)
        {
            e.printStackTrace();
        }catch (Throwable th)
        {
            th.printStackTrace();
        }

        ret.put("url", url);
        ret.put("jsapi_ticket", jsapi_ticket);
        ret.put("nonceStr", nonce_str);
        ret.put("timestamp", timestamp);
        ret.put("signature", signature);

        return ret;
    }

	
	/**
	* @Title: byteToHex 
	* @Description: 根据hash换算
	* @param @param hash
	* @param @return   
	* @return String
	* @throws
	 */
    private static String byteToHex(final byte[] hash) {
        Formatter formatter = new Formatter();
        for (byte b : hash)
        {
            formatter.format("%02x", b);
        }
        String result = formatter.toString();
        formatter.close();
        return result;
    }

    /**
     * 
    * @Title: create_nonce_str 
    * @Description: 拿到uuid
    * @param @return   
    * @return String
    * @throws
     */
    private static String create_nonce_str() {
        return UUID.randomUUID().toString();
    }

    
    /**
     * 
    * @Title: create_timestamp 
    * @Description: 创建时间
    * @param @return   
    * @return String
    * @throws
     */
    private static String create_timestamp() {
        return Long.toString(System.currentTimeMillis() / 1000);
    }

	/**
	 * 根据内容类型判断文件扩展名
	 * @param contentType 内容类型
	 * @return
	 */
	public static String getFileExt(String contentType) {
		String fileExt = "";
		if ("image/jpeg".equals(contentType))
			fileExt = ".jpg";
		else if ("audio/mpeg".equals(contentType))
			fileExt = ".mp3";
		else if ("audio/amr".equals(contentType))
			fileExt = ".amr";
		else if ("video/mp4".equals(contentType))
			fileExt = ".mp4";
		else if ("video/mpeg4".equals(contentType))
			fileExt = ".mp4";
		return fileExt;
	}
	
	
	
}
