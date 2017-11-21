package com.paic.qhcs.test;


import com.alibaba.fastjson.JSONObject;

/**
 * 请求示例参考
 * 
 * @author 唐应泉
 * @support by Tel:0755-22625539
 * @company 深圳前海征信中心股份有限公司
 * 
 */
public class DemoMain
{
    public static void main(String[] args) throws Exception
    {
        // 发送HTTP请求
        postHttpRequest();
        // 发送HTTPS请求
        postHttpsRequest();
    }

    /**
     * 发送HTTP请求
     * 
     * @throws Exception
     */
    private static void postHttpRequest() throws Exception
    {
        String sfUrl = "http://localhost:7012/dcs-dmz/do/dmz/query/blacklist/v1/MSC8004";
        String header = "\"header\":" + MessageUtil.getMHeader_DMZ();
        String encBusiData = DataSecurityUtil.encrypt(MessageUtil.getBusiData_MSC8004().getBytes(),
                "SK803@!QLF-D25WEDA5E52DA");
        String busiData = "\"busiData\":\"" + encBusiData + "\"";
        String sigValue = DataSecurityUtil.signData(encBusiData);
        String pwd = DataSecurityUtil.digest("weblogic1".getBytes());
        String securityInfo = "\"securityInfo\":" + MessageUtil.getSecurityInfo(sigValue, pwd);
        String message = "{" + header + "," + busiData + "," + securityInfo + "}";
        System.out.println("请求：" + message);

        String res = HttpRequestUtil.sendJsonWithHttp(sfUrl, message);

        System.out.println("响应Message：" + res);
        JSONObject msgJSON = JSONObject.parseObject(res);
        //JSONObject msgJSON = JSONObject.fromObject(res);
        System.out.println("响应BusiData密文：" + msgJSON.getString("busiData"));
        // 一定要验证签名的合法性！！！！！！！！
        DataSecurityUtil.verifyData(msgJSON.getString("busiData"), msgJSON.getJSONObject("securityInfo").getString(
                "signatureValue"));

        System.out.println("响应BusiData明文："
                + DataSecurityUtil.decrypt(msgJSON.getString("busiData"), "SK803@!QLF-D25WEDA5E52DA"));
    }

    /**
     * 发送HTTPs请求,注意这里我们信任了任何服务器证书
     * 
     * @throws Exception
     */
    private static void postHttpsRequest() throws Exception
    {
        String sfUrl = "https://test-qhzx.pingan.com.cn:5443/do/dmz/query/blacklist/v1/MSC8004";
        String header = "\"header\":" + MessageUtil.getMHeader_DMZ();
        String encBusiData = DataSecurityUtil.encrypt(MessageUtil.getBusiData_MSC8004().getBytes(),
                "SK803@!QLF-D25WEDA5E52DA");
        String busiData = "\"busiData\":\"" + encBusiData + "\"";
        String sigValue = DataSecurityUtil.signData(encBusiData);
        String pwd = DataSecurityUtil.digest("weblogic1".getBytes());
        String securityInfo = "\"securityInfo\":" + MessageUtil.getSecurityInfo(sigValue, pwd);
        String message = "{" + header + "," + busiData + "," + securityInfo + "}";
        System.out.println("请求：" + message);

        String res = HttpRequestUtil.sendJsonWithHttps(sfUrl, message);

        System.out.println("响应Message：" + res);
        JSONObject msgJSON = JSONObject.parseObject(res);
        System.out.println("响应BusiData密文：" + msgJSON.getString("busiData"));

        // 一定要验证签名的合法性！！！！！！！！
        DataSecurityUtil.verifyData(msgJSON.getString("busiData"), msgJSON.getJSONObject("securityInfo").getString(
                "signatureValue"));

        System.out.println("响应BusiData明文："
                + DataSecurityUtil.decrypt(msgJSON.getString("busiData"), "SK803@!QLF-D25WEDA5E52DA"));
    }
}
