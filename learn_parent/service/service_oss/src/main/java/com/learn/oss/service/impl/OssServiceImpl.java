package com.learn.oss.service.impl;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.CannedAccessControlList;
import com.learn.oss.service.OssService;
import com.learn.oss.utils.ConstantPropertiesUtil;
import com.learn.oss.utils.OssProperties;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
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
     * 从配置文件读取  常量属性读取工具类--->第二种方法
     */
    @Autowired
    private OssProperties ossProperties;

    /**
     * 上传头像到oss--->第一种
     *
     * @param file
     * @return 回上传到oss的路径
     */
    /*@Override
    public String uploadFileAvater(MultipartFile file) {
        // Endpoint以杭州为例，其它Region请按实际情况填写。
        String endpoint = ConstantPropertiesUtil.END_POINT;
        // 云账号AccessKey有所有API访问权限，建议遵循阿里云安全最佳实践，创建并使用RAM子账号进行API访问或日常运维，请登录 https://ram.console.aliyun.com 创建。
        String accessKeyId = ConstantPropertiesUtil.KEY_ID;
        String accessKeySecret = ConstantPropertiesUtil.KEY_SECRET;
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
            String uuid = UUID.randomUUID().toString().replaceAll("-", "");
            fileName = uuid + fileName;

            //2、把文件按照日期进行分类存储
            //获取当前日期
            String datePath = new DateTime().toString("yyyy/MM/dd");
            //拼接
            fileName = datePath + "/" + fileName;

            //调用oss方法实现上传
            //参数1：bucket名称  参数2：上传到oss文件路径和名称  例:/aa/bb/1.jpg  参数3：上传文件输入流
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
    }*/

    /**
     * 阿里云oos文件上传--->第二种
     *
     * @param inputStream      输入流
     * @param module           文件夹名称
     * @param originalFilename 原始文件名
     * @return 文件在oos服务器上的url地址
     */
    @Override
    public String upload(InputStream inputStream, String module, String originalFilename) {
        //读取配置信息
        String endpoint = ossProperties.getEndpoint();
        String keyid = ossProperties.getKeyid();
        String keysecret = ossProperties.getKeysecret();
        String bucketname = ossProperties.getBucketname();
        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, keyid, keysecret);
        //首先判断bucket存在不，如果不存在则创建
        if (!ossClient.doesBucketExist(bucketname)) {
            ossClient.createBucket(bucketname);
            ossClient.setBucketAcl(bucketname, CannedAccessControlList.PublicRead);
        }

        //构建objectName：文件路径 2020/06/17/46348b9e0caa4e838d0325101a792941file.png
        //把文件按照日期进行分类存储
        //获取当前日期
        String floder = new DateTime().toString("yyyy/MM/dd");
        //拼接
        String fileName = UUID.randomUUID().toString();
        String fileExtesion = originalFilename.substring(originalFilename.lastIndexOf("."));
        String key = module + "/" + floder + "/" + fileName + fileExtesion;

        //调用oss方法实现上传
        //参数1：bucket名称  参数2：上传到oss文件路径和名称  例:/aa/bb/1.jpg  参数3：上传文件输入流
        ossClient.putObject(bucketname, key, inputStream);

        // 关闭OSSClient。
        ossClient.shutdown();

        //返回url https://dlqedu-01.oss-cn-beijing.aliyuncs.com/2020/06/17/46348b9e0caa4e838d0325101a792941file.png
        return "https://" + bucketname + "." + endpoint + "/" + key;
    }

    @Override
    public void removeFile(String url) {
        //读取配置信息
        String endpoint = ossProperties.getEndpoint();
        String keyid = ossProperties.getKeyid();
        String keysecret = ossProperties.getKeysecret();
        String bucketname = ossProperties.getBucketname();
        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, keyid, keysecret);

        // 删除文件。
        String host="https://" + bucketname + "." + endpoint + "/";
        String objectName = url.substring(host.length());
        ossClient.deleteObject(bucketname, objectName);

        // 关闭OSSClient。
        ossClient.shutdown();
    }
}
