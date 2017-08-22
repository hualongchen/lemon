package com.tr.file.web.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.qcloud.cos.COSClient;

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
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;


@Slf4j
@Controller
public class FileController {


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

            return "file";
        }
        return "file";
    }


    /**
     * 测试存放到服务器本地
     * @param request
     * @param file
     * @param form
     * @return
     */
    @PostMapping("/upload2")
    @ResponseBody
    @SneakyThrows
    public ResultVO upLoadFile2(HttpServletRequest request, @RequestParam("file") MultipartFile file, FileForm form) {


        String fileName = file.getOriginalFilename();

        if (null != fileName && fileName.trim() != "") {

            //byte[] contentBuffer = file.getBytes();

            File file2  = new File("/Users/chenhualong/Documents/dev_tool/"+fileName);

            
            

            file.transferTo(file2);
        }


        return new ResultVO();
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
         * 进行文件的上传
         */

        COSClient client = TencentFileUtil.getClient();

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


        if (null != fileName && fileName.trim() != "") {

            byte[] contentBuffer = file.getBytes();

            UploadFileRequest FileRequest = new UploadFileRequest(bucketName, cosFilePath, contentBuffer);

            String uploadFileRet = client.uploadFile(FileRequest);

            JSONObject jsonObject = JSON.parseObject(uploadFileRet);

            TencentFileUtil.closeClient(client);

            if (!jsonObject.getString("code").equals("200")) {

                return new ResultVO(ErrorContent.FILE_UPLOAD_FAILE_CODE, ErrorContent.FILE_UPLOAD_FAILE_MESSAGE);
            }

        }

        return service.uploadFile(form);
    }


}
