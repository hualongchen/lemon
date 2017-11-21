package com.tr.file.web.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.qcloud.cos.COSClient;

import com.qcloud.cos.meta.InsertOnly;
import com.qcloud.cos.request.*;
import com.tr.file.modul.file.FileForm;
import com.tr.file.service.FileService;
import com.tr.file.util.CommonUtil;
import com.tr.file.util.ErrorContent;
import com.tr.file.util.ResultVO;
import com.tr.file.util.tencent.TencentFileUtil;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;


@Slf4j
@Controller
public class FileController {

    @Value("${tencent.appId}")
    private long appId;

    @Value("${tencent.secretId}")
    private String secretId;

    @Value("${tencnet.secretKey}")
    private String secretKey;


    @Autowired
    private FileService service;


    /**
     * 进行上传页面的路由
     *
     * @param request
     * @return
     */
    @GetMapping("/file")
    public String routToFile(HttpServletRequest request) {

        /**
         * 查看是否登陆
         */
        HttpSession session = request.getSession();

        if (null == session.getAttribute("TR_USER")) {

            return "login";
        }
        return "file";
    }


    /**
     * 进行上传文件操作，存放到腾讯云
     *
     * @param request
     * @param file
     * @param form
     * @return
     */
    @PostMapping("/upload")
    @ResponseBody
    @SneakyThrows
    public ResultVO upLoadFile(HttpServletRequest request, @RequestParam("file") MultipartFile file, FileForm form) {


        /**
         * 验证是否登陆
         */
        HttpSession session = request.getSession();

        if (null == session.getAttribute("TR_USER")) {

            return new ResultVO(ErrorContent.USER_NOT_LOGIN_CODE, ErrorContent.USER_NOT_LOGIN_MESSAGE);
        }

        String fileName = file.getOriginalFilename();

        /**
         * 检查是否上传文件
         */
        if (null == fileName || fileName.trim() == "") {

            return new ResultVO(ErrorContent.FILE_NOTEXIT_CODE, ErrorContent.FILE_NOTEXIT_CODE_MESSAGE);
        }

        //目录
        String bucketName = "compact";
        //存储到腾讯云的名字,规则【合同号.avi】
        String cosFilePath = null;

        String lastName = CommonUtil.getFileLastName(fileName);

        if (null != lastName) {

            cosFilePath = "/" + form.getArgeementid() + lastName;
        } else {

            cosFilePath = "/" + form.getArgeementid() + ".avi";
        }


        /**
         * 将MultipartFile 转化为File 存储到本地临时目录
         */
        String localFilePath = "/Users/chenhualong/Desktop" + cosFilePath;
        File loacFile = new File(localFilePath);

        file.transferTo(loacFile);


        /**
         * 进行文件的上传
         */

        String uploadFileRet = TencentFileUtil.uploadFile(bucketName,cosFilePath,localFilePath,appId,secretId,secretKey);

        JSONObject jsonObject = JSON.parseObject(uploadFileRet);

        log.info("上传文件后结果:" + jsonObject.toString());

        if (!jsonObject.getString("code").equals("0")) {

            return new ResultVO(ErrorContent.FILE_UPLOAD_FAILE_CODE, ErrorContent.FILE_UPLOAD_FAILE_MESSAGE);
        }

        JSONObject jsonObject1 = jsonObject.getJSONObject("data");

        form.setAccessUrl(jsonObject1.getString("access_url"));
        form.setResourcePath(jsonObject1.getString("resource_path"));
        form.setVid(jsonObject1.getString("vid"));
        form.setSourceUrl(jsonObject1.getString("source_url"));

        /**
         * 删除临时文件
         */
        if (loacFile.isFile() && loacFile.exists()) {

            loacFile.delete();
        }

        /**
         * 存库绑定关系
         */

        return service.uploadFile(form);
    }


}
