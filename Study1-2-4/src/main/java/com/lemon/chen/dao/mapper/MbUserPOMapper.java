package com.lemon.chen.dao.mapper;

import com.lemon.chen.dao.bean.MbUserPO;
import com.lemon.chen.dao.bean.MbUserPOExample;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;


public interface MbUserPOMapper {


    int countByExample(MbUserPOExample example);

    int deleteByExample(MbUserPOExample example);

    int deleteByPrimaryKey(Integer userid);

    int insert(MbUserPO record);

    int insertSelective(MbUserPO record);

    List<MbUserPO> selectByExample(MbUserPOExample example);

    MbUserPO selectByPrimaryKey(Integer userid);

    int updateByExampleSelective(@Param("record") MbUserPO record, @Param("example") MbUserPOExample example);

    int updateByExample(@Param("record") MbUserPO record, @Param("example") MbUserPOExample example);

    int updateByPrimaryKeySelective(MbUserPO record);

    int updateByPrimaryKey(MbUserPO record);
}