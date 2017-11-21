/**
 * 
 */
package com.paic.qhcs.test;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;

/**
 * SSL连接上下文
 * 
 * @author 唐应泉(0755-22625539)
 * 
 */
public class SSLContextFactory
{
    private static SSLContext ctx;
    private final static String PROTOCAL_NAME = "SSL";

    public static SSLContext getInstance(boolean chkCert)
    {
        if (ctx == null)
        {
            try
            {
                ctx = SSLContext.getInstance(PROTOCAL_NAME);
                if (chkCert)
                {
                    System.err.println("您要实现证书信任连接，请联系我们的管理员！");
                }
                else
                {
                    ctx.init(null, new TrustManager[] { new TrustAnyTrustManager() }, new SecureRandom());
                }
            } catch (NoSuchAlgorithmException e)
            {
                e.printStackTrace();
            } catch (KeyManagementException e)
            {
                e.printStackTrace();
            }
            return ctx;
        }
        return ctx;
    }
}
