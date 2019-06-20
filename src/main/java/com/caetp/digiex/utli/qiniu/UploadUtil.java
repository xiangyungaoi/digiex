package com.caetp.digiex.utli.qiniu;

import com.caetp.digiex.consts.StaticProperties;
import com.caetp.digiex.utli.common.Utility;
import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;


public class UploadUtil {
    // 设置好账号的ACCESS_KEY和SECRET_KEY
	private static String  ACCESS_KEY = StaticProperties.QI_NIU_ACCESS_KEY;
	private static String SECRET_KEY = StaticProperties.QI_NIU_SECRET_KEY;
    // 要上传的空间
	private static String BUCKET = StaticProperties.QI_NIU_BUCKET;
    private static String DOMAIN_URL = StaticProperties.QI_NIU_DOMAIN_URL;

    // 密钥配置
    private static Auth AUTH = Auth.create(ACCESS_KEY, SECRET_KEY);
    
    // 指定上传的Zone的信息
    // 第一种方式: 指定具体的要上传的zone
    // 注：该具体指定的方式和以下自动识别的方式选择其一即可
    // 要上传的空间(bucket)的存储区域为华东时
    private static Zone ZONE = Zone.zone0();
    // 要上传的空间(bucket)的存储区域为华北时
    // Zone ZONE = Zone.zone1();
    // 要上传的空间(bucket)的存储区域为华南时
    // Zone ZONE = Zone.zone2();

    // 第二种方式: 自动识别要上传的空间(bucket)的存储区域是华东、华北、华南。
    // Zone ZONE = Zone.autoZone();
    private static Configuration CONFIGURATION = new Configuration(ZONE);

    // 创建上传对象
    private static UploadManager UPLOADMANAGER = new UploadManager(CONFIGURATION);

    // 简单上传，使用默认策略，只需要设置上传的空间名就可以了
    public static  String getUpToken() {
        return AUTH.uploadToken(BUCKET);
    }

    public static String uploadFile(MultipartFile file) {
        String fileUrl = null;
        String fileName = Utility.generateUuid()+
                file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
        try {
            if (uploadStream(file.getInputStream(), fileName)) {
                fileUrl = DOMAIN_URL + fileName;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return fileUrl;
    }

    private static boolean uploadStream(InputStream source, String fileName){
    	boolean result = false;
		try {
		    Response response = UPLOADMANAGER.put(source, fileName, getUpToken(),null, null);
		    //解析上传成功的结果
		    DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
		    result = true;
		} catch (QiniuException e) {
		    e.printStackTrace();
		}
		return result;
    }

    //删除文件
    public static boolean delete(String key){
    	Configuration config = new Configuration(Zone.autoZone());
        BucketManager bucketMgr = new BucketManager(AUTH, config);
        boolean result = false;
        try {
			bucketMgr.delete(BUCKET, key);
			result = true;
		} catch (QiniuException e) {
			e.printStackTrace();
		}
        return result;
    }

    public static String appGetUploadToken(String bucket, String key) {
        String uploadToken;
        if (key != null && !key.isEmpty()) {
            uploadToken = AUTH.uploadToken(bucket, key);
        } else {
            uploadToken = AUTH.uploadToken(bucket);
        }
        return uploadToken;
    }

}
