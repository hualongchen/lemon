package com.paic.qhcs.test;

public class MessageUtil
{
    public static String getMHeader()
    {
        StringBuffer sb = new StringBuffer(
                "{\"orgCode\":\"3\",\"chnlId\":\"qhcs-dcs\",\"transNo\":\"Tran001\",\"transDate\":\"2015-02-02 14:12:14\",\"authCode\":\"P2P001A2_123\",\"authDate\":\"2015-12-02 14:12:14\"}");
        return sb.toString();
    }
    
    public static String getMHeader_DMZ()
    {
        StringBuffer sb = new StringBuffer(
                "{\"orgCode\":\"10000000\",\"chnlId\":\"qhcs-dcs-dmz\",\"transNo\":\"Trandsfsdf1\",\"transDate\":\"2015-02-02 14:12:14\",\"authCode\":\"CRT001A3_123\",\"authDate\":\"2015-12-02 14:12:14\"}");
        return sb.toString();
    }

    public static String getBusiData()
    {
        StringBuffer sb = new StringBuffer(
                "{\"batchNo\":\"33adfsf323233\",\"records\":[{\"reasonCode\":\"01\",\"idNo\":\"362528198745421654\",\"idType\":\"0\",\"name\":\"唐唐\",\"seqNo\":\"r231545334545\"}]}");
        return sb.toString();
    }
    
    public static String getBusiData_MSC8004()
    {
        StringBuffer sb = new StringBuffer(
                "{\"batchNo\":\"33adfsf323233\",\"records\":[{\"reasonCode\":\"01\",\"idNo\":\"362528198745421654\",\"idType\":\"0\",\"name\":\"唐唐\",\"seqNo\":\"r231545334545\"}]}");
        return sb.toString();
    }

    public static String getSecurityInfo(String signatureValue, String pwd)
    {
        StringBuffer sb = new StringBuffer("{\"signatureValue\":\"" + signatureValue
                + "\",\"userName\":\"weblogic\",\"userPassword\":\"" + pwd + "\"}");
        return sb.toString();
    }
}
