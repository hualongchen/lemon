package com.lemon.chen.biz;

import com.lemon.chen.content.testForm;
import com.lemon.chen.dao.bean.MbUserPO;
import com.lemon.chen.dao.bean.MbUserPOExample;
import com.lemon.chen.dao.mapper.MbUserPOMapper;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by chenhualong on 16/5/4.
 */
@Service
public class FirstBizServer {



    @Autowired
    private MbUserPOMapper userPOMapper ;




    /**
     * 第一个业务方法
     */
    public MbUserPO firstBizserverService(){

        System.out.println("this is first biz  maven project");

        MbUserPOExample example = new MbUserPOExample();

        example.createCriteria().andUseridEqualTo(1);

        return userPOMapper.selectByExample(example).get(0);

    }

    /**
     * 第二个修改方法的业务
     */
    @Transactional
    public void secondServerService(testForm form){

        //属性转换
        MbUserPO  po = new MbUserPO();
        try{
            BeanUtils.copyProperties(po,form);
        }catch (Exception e){
            e.printStackTrace();
        }
        MbUserPOExample  example = new MbUserPOExample();
        example.createCriteria().andUseridEqualTo(po.getUserid());

        userPOMapper.updateByExample(po,example);

        throw  new RuntimeException("fuck you ");
    }


}
