package com.paic.qhcs.test;

import java.util.Map;
import java.util.regex.Pattern;


import com.alibaba.fastjson.JSONObject;
import org.apache.commons.beanutils.BeanUtils;

/**
 * 系统工具类
 * 
 * @author TANGYINGQUAN360
 * 
 */
public class CommonUtil
{
    @SuppressWarnings("unchecked")
    public static String describe(Object obj) throws Exception
    {
        Map<String, String> objMap = BeanUtils.describe(obj);
        return objMap.toString();
    }

    public static <T> T parseJSON2Bean(String jsonStr, Class<T> clazz)
    {
        JSONObject json = JSONObject.parseObject(jsonStr);
        return JSONObject.toJavaObject(json, clazz);
    }

    public static boolean checkIP(String s)
    {
        Pattern pattern = Pattern.compile("^((\\d|[1-9]\\d|1\\d\\d|2[0-4]\\d|25[0-5]"
                + "|[*])\\.){3}(\\d|[1-9]\\d|1\\d\\d|2[0-4]\\d|25[0-5]|[*])$");
        return pattern.matcher(s).matches();
    }

    public static String bytes2Hex(byte[] inbuf)
    {
        int i;
        String byteStr;
        StringBuffer strBuf = new StringBuffer();
        for (i = 0; i < inbuf.length; i++)
        {
            byteStr = Integer.toHexString(inbuf[i] & 0x00ff);
            if (byteStr.length() != 2)
            {
                strBuf.append('0').append(byteStr);
            }
            else
            {
                strBuf.append(byteStr);
            }
        }
        return new String(strBuf);
    }

    public static byte[] hexToBytes(String inbuf)
    {
        int i;
        int len = inbuf.length() / 2;
        byte outbuf[] = new byte[len];
        for (i = 0; i < len; i++)
        {
            String tmpbuf = inbuf.substring(i * 2, i * 2 + 2);

            outbuf[i] = (byte) Integer.parseInt(tmpbuf, 16);
        }
        return outbuf;
    }
}
