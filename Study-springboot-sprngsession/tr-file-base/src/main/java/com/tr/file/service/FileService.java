package com.tr.file.service;


import com.tr.file.dao.bean.file.TrFilePO;
import com.tr.file.dao.mapper.file.TrFilePOMapper;
import com.tr.file.modul.file.FileForm;
import com.tr.file.util.ErrorContent;
import com.tr.file.util.ResultVO;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class FileService {

    @Autowired
    private TrFilePOMapper filePOMapper;


    /**
     * 将所有的关系存入数据库
     *
     * @param form
     * @return
     */
    @SneakyThrows
    @Transactional
    public ResultVO uploadFile(FileForm form) {


        TrFilePO po = new TrFilePO();
        /**
         * 关联关系进行存库
         */
        po.setArgeementid(po.getArgeementid());

        if (null == form.getArgeementid()) {
            return new ResultVO(ErrorContent.FILE_UPLOAD_FAILE_CODE, ErrorContent.USER_NOT_LOGIN_MESSAGE);

        }
        po.setArgeementid(form.getArgeementid());
        po.setAccessurl(form.getAccessUrl());
        po.setFilevid(form.getVid());
        po.setSourcepath(form.getResourcePath());
        po.setSourceurl(form.getSourceUrl());
        po.setUsername(form.getUsername());

        filePOMapper.insertSelective(po);

        return new ResultVO();
    }
}
