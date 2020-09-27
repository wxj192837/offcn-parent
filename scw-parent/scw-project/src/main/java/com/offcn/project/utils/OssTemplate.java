package com.offcn.project.utils;


import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NegativeOrZero;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OssTemplate {
    private String endpoint;
    private String accessKeyId;
    private String accessKeySecret;
    private String bucketName;
    private String bucketDomain;

    //上传
    public String upload(InputStream is,String fileName){
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        //定义一个有时间的名字
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        //目录的名字
        String s = format.format(new Date());
        //文件的名字
        fileName = UUID.randomUUID().toString().replace("-", "") + "_" + fileName;
        ossClient.putObject(bucketName,"pic/"+ s +"/"+fileName,is);
        //关
        ossClient.shutdown();

        try {
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //获取文件的存储路径
        return  "https://"+bucketDomain+"pic/"+ s +"/"+fileName;
    }
}
