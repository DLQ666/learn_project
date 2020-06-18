package com.learn.oss.controller;

import com.learn.oss.service.OssService;
import com.learn.utils.result.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @program: learn_parent
 * @description: OssController层
 * @author: Hasee
 * @create: 2020-06-17 12:46
 *
 * @CrossOrigin: 解决跨域问题加这个注解
 */
@CrossOrigin
@RestController
@RequestMapping("/eduoss/fileoss")
public class OssController {

    @Autowired
    private OssService ossService;

    /**
     * 上传头像方法
     * @param file
     * @return 返回上传到oss的路径
     */
    @PostMapping()
    public ResponseResult uploadOssFile(MultipartFile file){
        //获取上传文件 MultipartFile
        //返回上传到oss的路径
        String url=ossService.uploadFileAvater(file);
        return ResponseResult.ok().data("url",url);
    }

}
