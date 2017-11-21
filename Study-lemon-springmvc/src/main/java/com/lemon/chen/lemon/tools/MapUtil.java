package com.lemon.chen.lemon.tools;

/**
 * Created by chenhualong on 16/7/16.
 */
public class MapUtil {


    private static String ak ="wh8Gqdc7NZPMv8W2z0doxhsh";

    private static String  coor ="bd09ll";

    /**
     * 根据IP查询百度所在的地址。
     * @param ip  ip
     * @return
     */
    public static String getAddrByIp(String ip,String  ak , String coor){

        String  ipUrl ="http://api.map.baidu.com/location/ip?ak="+ak+"&ip="+ip+"&coor="+coor;


        return null ;
    }




}

