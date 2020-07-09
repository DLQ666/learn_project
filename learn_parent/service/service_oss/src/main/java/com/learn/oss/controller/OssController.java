package com.learn.oss.controller;

import com.learn.oss.service.OssService;
import com.learn.service.base.exception.CustomException;
import com.learn.utils.result.ResponseResult;
import com.learn.utils.result.ResultCodeEnum;
import com.learn.utils.utils.ExceptionUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.concurrent.TimeUnit;

/**
 * @program: learn_parent
 * @description: OssController层
 * @author: Hasee
 * @create: 2020-06-17 12:46
 *
 * @CrossOrigin: 解决跨域问题加这个注解
 */
@Api(description = "阿里云文件管理")
@RestController
@RequestMapping("/eduoss/fileoss")
@Slf4j
public class OssController {

    @Autowired
    private OssService ossService;

    /**
     * 上传头像方法--->1
     * @param file
     * @return 返回上传到oss的路径
     */
    /*@PostMapping()
    public ResponseResult uploadOssFile(MultipartFile file){
        //获取上传文件 MultipartFile
        //返回上传到oss的路径
        String url=ossService.uploadFileAvater(file);
        return ResponseResult.ok().data("url",url);
    }*/

    @ApiOperation("文件上传")
    @PostMapping("/upload")
    public ResponseResult upload(@RequestParam("file") MultipartFile file,@RequestParam("module") String module) {
        try {
            InputStream inputStream = file.getInputStream();
            String originalFilename = file.getOriginalFilename();
            String uploadUrl = ossService.upload(inputStream, module, originalFilename);
            return ResponseResult.ok().message("文件上传成功").data("url",uploadUrl);
        } catch (Exception e) {
            log.error(ExceptionUtil.getMessage(e));
            throw new CustomException(ResultCodeEnum.FILE_UPLOAD_ERROR);
        }
    }

    @ApiOperation(value = "从oss服务器删除讲师头像")
    @DeleteMapping("/remove")
    public ResponseResult removeFile(@RequestBody String url){
        ossService.removeFile(url);
        return ResponseResult.ok().message("文件删除成功");
    }

    @ApiOperation(value = "测试")
    @GetMapping("/test")
    public ResponseResult test() {
        log.info("oss test被调用");
        try { TimeUnit.SECONDS.sleep(3); } catch (InterruptedException e) { e.printStackTrace(); }
        return ResponseResult.ok();
    }


}
