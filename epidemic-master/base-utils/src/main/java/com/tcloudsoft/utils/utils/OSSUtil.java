package com.tcloudsoft.utils.utils;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.tcloudsoft.utils.TcloudUtils;
import com.tcloudsoft.utils.ex.ResponseCodeEnum;
import com.tcloudsoft.utils.response.ResponseData;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * OSS对象存储
 */
public class OSSUtil {

    private static final String key="tcloud/";

    private static final String endpoint="oss-cn-beijing.aliyuncs.com";

    private static final String accessKeyId="LTAI5tRomt24suDrBHy5pXAU";

    private static final String accessKeySecret="l7EOTLQxWdT3zeu3tLzl65B8wKkE80";

    private static final String bucketName="tcloudsoft";


    /**
     * 上传文件
     * @param files 文件数组
     * @return
     */
    public static ResponseData<Object> imgUpload(MultipartFile files[]){
        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        Map<String,String> map = new HashMap<>();
        try {
            if(TcloudUtils.isEmpty(files)){
                return ResponseData.fail(ResponseCodeEnum.UP0002);
            }
            for(MultipartFile file:files){
                String filename = file.getOriginalFilename();
                //创建PutObjectRequest对象。
                byte [] byteArr=file.getBytes();
                // 上传文件流。
                InputStream inputStream = new ByteArrayInputStream(byteArr);
                ossClient.putObject(bucketName, key+filename, inputStream);
                map.put("filename",filename);
                map.put("url","http://guanwang.tcloudsoft.com/tcloud/"+filename);
            }
            // 关闭OSSClient。
            ossClient.shutdown();
            return ResponseData.ok(map);
        }catch (Exception e){
            ossClient.shutdown();
            return ResponseData.fail(ResponseCodeEnum.UP0001);
        }
    }
}
