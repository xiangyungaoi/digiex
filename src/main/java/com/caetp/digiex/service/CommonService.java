package com.caetp.digiex.service;

import com.caetp.digiex.exception.CommonException;
import com.caetp.digiex.utli.qiniu.UploadUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 * Created by Andy on 2018/7/27.
 */
@Service
public class CommonService extends BaseService {

    /**
     * 上传文件
     * @param file
     * @return
     */
    public String uploadFile(MultipartFile file) {

        String fileUrl = UploadUtil.uploadFile(file);
        if (fileUrl == null) {
            throw CommonException.SAVE_FAIL;
        }
        return fileUrl;
    }

}
