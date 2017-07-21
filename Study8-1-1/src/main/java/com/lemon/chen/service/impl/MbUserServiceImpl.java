package com.lemon.chen.service.impl;

import com.lemon.chen.core.AbstractService;
import com.lemon.chen.dao.MbUserMapper;
import com.lemon.chen.model.MbUser;
import com.lemon.chen.service.MbUserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;


/**
 * Created by chenhualong on 2017/07/05.
 */
@Service
@Transactional
public class MbUserServiceImpl extends AbstractService<MbUser> implements MbUserService {
    @Resource
    private MbUserMapper mbUserMapper;




}
