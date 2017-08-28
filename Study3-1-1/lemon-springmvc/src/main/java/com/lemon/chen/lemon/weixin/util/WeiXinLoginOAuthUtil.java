package com.lemon.chen.lemon.weixin.util;

/**
 * Created by chenhualong on 16/6/20.
 */
public class WeiXinLoginOAuthUtil {


    //第一步请求 code

    String   first ="https://open.weixin.qq.com/connect/qrconnect?appid=APPID&redirect_uri=REDIRECT_URI&response_type=code&scope=SCOPE&state=STATE#wechat_redirect";


    //重定向跳转后，根据code获取token：

    String  second ="https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code";


    //返回值  ： {
    // "access_token":"ACCESS_TOKEN",
     //       "expires_in":7200,
     //       "refresh_token":"REFRESH_TOKEN",
     //       "openid":"OPENID",
      //      "scope":"SCOPE",
      //      "unionid": "o6_bmasdasdsad6_2sgVt7hMZOPfL"
//}



}
