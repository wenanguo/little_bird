package com.cmtt.base.service.impl;


import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.model.PutObjectRequest;
import com.qcloud.cos.model.PutObjectResult;
import com.qcloud.cos.region.Region;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.File;

/**
 * <p>
 * 腾讯云 服务实现类
 * </p>
 *
 * @author Andrew.Wen
 * @since 2020-11-25
 */
@Service
public class TencentServiceImpl {

    private final Logger logger = LoggerFactory.getLogger(TencentServiceImpl.class);




    private String SECRETID = "AKIDf5UhNq6M1PYQ2RSU7E7i9IEsFLwJ8Ci2";
    private String SECRETKEY = "D9ojOEn4TdV5zf5E7xYrtLYZIkJceOL8";
    // 区域 上海
    private String REGION_CODE="ap-shanghai";

    // 存储桶名称
    private String BUCKET_NAME = "littlebird-1251508131";

    //外网访问地址
    private String URL = "https://"+BUCKET_NAME+".cos."+REGION_CODE+".myqcloud.com/";


    // 腾讯云客户端
    private COSClient cosClient;


    TencentServiceImpl(){

        COSCredentials cred = new BasicCOSCredentials(this.SECRETID, this.SECRETKEY);
        // 2 设置 bucket 的区域, COS 地域的简称请参照 https://cloud.tencent.com/document/product/436/6224
        // clientConfig 中包含了设置 region, https(默认 http), 超时, 代理等 set 方法, 使用可参见源码或者常见问题 Java SDK 部分。
        Region region = new Region(this.REGION_CODE);
        ClientConfig clientConfig = new ClientConfig(region);
        // 3 生成 cos 客户端。
        this.cosClient = new COSClient(cred, clientConfig);
        logger.info("腾讯云服务初始化完成");
    }


    /**
     * 获取腾讯云客户端
     * @return
     */
    public COSClient getCosClient() {
        return cosClient;
    }

    /**
     * 上传图片文件，返回公网访问路径
     * @param file
     * @return
     */
    public String uploadImg(File file){


        //获取文件后缀名
        String suffix = file.getName().substring(file.getName().lastIndexOf(".") + 1);

        // 指定要上传到 COS 上对象键
        String key = "images/"+String.valueOf(System.currentTimeMillis())+"."+suffix;
        PutObjectRequest putObjectRequest = new PutObjectRequest(this.BUCKET_NAME, key, file);
        PutObjectResult putObjectResult = this.cosClient.putObject(putObjectRequest);

        System.out.println(putObjectResult);

        return URL+key;
    }
}
