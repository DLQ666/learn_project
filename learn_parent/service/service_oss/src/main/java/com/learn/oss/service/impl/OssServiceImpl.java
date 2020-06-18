package com.learn.oss.service.impl;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.learn.oss.service.OssService;
import com.learn.oss.utils.ConstantPropertiesUtil;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.UUID;

/**
 * @program: learn_parent
 * @description: Service层实现类
 * @author: Hasee
 * @create: 2020-06-17 12:47
 */
@Service
public class OssServiceImpl implements OssService {

    /**
     * 上传头像到oss
     * @param file
     * @return 回上传到oss的路径
     */
    @Override
    public String uploadFileAvater(MultipartFile file) {
        // Endpoint以杭州为例，其它Region请按实际情况填写。
        String endpoint = ConstantPropertiesUtil.END_POINT;
        // 云账号AccessKey有所有API访问权限，建议遵循阿里云安全最佳实践，创建并使用RAM子账号进行API访问或日常运维，请登录 https://ram.console.aliyun.com 创建。
        String accessKeyId = ConstantPropertiesUtil.ACCESS_KEY_ID;
        String accessKeySecret = ConstantPropertiesUtil.ACCESS_KEY_SECRET;
        String bucketName = ConstantPropertiesUtil.BUCKET_NAME;

        InputStream inputStream = null;
        try {
            // 创建OSSClient实例。
            OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
            // 获取上传文件输入流。
            inputStream = file.getInputStream();
            //获取文件名称
            String fileName = file.getOriginalFilename();

            //1、在文件名称中添加随机唯一值
            String uuid = UUID.randomUUID().toString().replaceAll("-","");
            fileName=uuid+fileName;

            //2、把文件按照日期进行分类存储
            //获取当前日期
            String datePath = new DateTime().toString("yyyy/MM/dd");
            //拼接
            fileName = datePath + "/" + fileName;

            //调用oss方法实现上传
            //参数1：bucket名称  参数2：上传到oss文件路径和名称  /aa/bb/1.jpg  参数3：上传文件输入流
            ossClient.putObject(bucketName, fileName, inputStream);
            // 关闭OSSClient。
            ossClient.shutdown();

            //把文件上传之后文件路径返回
            //需要把上传到阿里云oss路径手动拼接出来
            //https://dlqedu-01.oss-cn-beijing.aliyuncs.com/f7901efc5f32d9c73a14828b601be7a5392aa24c.jpg%40320w_200h.webp.jpg
            String url = "https://" + bucketName + "." + endpoint + "/" + fileName;
            return url;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
