package com.lemon.chen.lemon.memcache;

import com.lemon.chen.lemon.tools.ResultVO;
import net.rubyeye.xmemcached.MemcachedClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * Created by chenhualong on 16/7/20.
 * 缓存公用类
 */

//@Component
public class MemcachedUtil {


    private static final Logger logger = LoggerFactory.getLogger(MemcachedUtil.class);


    @Autowired
    private MemcachedClient memcachedClient ;



    /**
     * 存入缓存
     *   timeKey : 缓存时间key
     *     cacheTime: 缓存真实的时间差
     *    valueKey: 缓存的值对应的key
     *    resultVo : 最终缓存的东西
     */
    public ResultVO setDataToMc(String timeKey,int cacheTime,String valueKey, ResultVO  resultVO){


        ResultVO  vo = new ResultVO();

        try{

            memcachedClient.add(timeKey,cacheTime,new Date().getTime());
            memcachedClient.add(valueKey,cacheTime,resultVO);

        }catch (Exception ex){

            ex.printStackTrace();
            logger.error("插入缓存失败", ex.getMessage());

            vo.setIsSuccess(false);
        }

        return  vo ;

    }

    /**
     *  直接利用MC时间做判断
     * @param cacheTime
     * @param valueKey
     * @param resultVO
     * @return
     */

    public ResultVO setDataToMemcache(int cacheTime,String valueKey, ResultVO  resultVO){


        ResultVO  vo = new ResultVO();

        try{

            memcachedClient.add(valueKey,cacheTime,resultVO);

        }catch (Exception ex){

            ex.printStackTrace();
            logger.error("插入缓存失败", ex.getMessage());

            vo.setIsSuccess(false);
        }

        return  vo ;

    }


    /**
     *
     * @param timeKey    缓存时间key
     * @param valueKey   缓存的值对应的key
     * @return
     */

    public  ResultVO getDataFromMC(String timeKey,long times,String valueKey){


        ResultVO  vo = new ResultVO();
        long cacheTime =0;

        try{

            //取自己设置的时间
             cacheTime = (Long)memcachedClient.get(timeKey);
        }catch (Exception ex){

             ex.printStackTrace();
            logger.error("获取memecached 时间信息出错",ex.getMessage());

            try{
                memcachedClient.delete(timeKey);
            }catch (Exception ex2){

                  ex2.printStackTrace();
                logger.error("删除缓存的数据出错", ex2.getMessage());
            }
        }

        //缓存还在作用期
        if( new Date().getTime()<(cacheTime+times)){

            //继续取剩下的值

            try{

                //取自己设置的时间
                vo =memcachedClient.get(valueKey);
            }catch (Exception ex){

                ex.printStackTrace();
                logger.error("获取memecached value信息出错",ex.getMessage());

                try{
                    memcachedClient.delete(valueKey);
                }catch (Exception ex2){
                    ex2.printStackTrace();
                    logger.error("删除缓存的数据出错",ex2.getMessage());
                }
            }
        }
        return vo ;

    }




    /**
     *
     * @param valueKey   缓存的值对应的key
     * @return
     */

    public  ResultVO getDataFromMemcache(String valueKey){


        ResultVO  vo = new ResultVO();

            try{

                //取自己设置的时间
                vo =memcachedClient.get(valueKey);
            }catch (Exception ex){

                ex.printStackTrace();
                logger.error("获取memecached value信息出错",ex.getMessage());

                try{
                    memcachedClient.delete(valueKey);
                }catch (Exception ex2){
                    ex2.printStackTrace();
                    logger.error("删除缓存的数据出错",ex2.getMessage());
                }
            }
        return vo ;

    }



}


