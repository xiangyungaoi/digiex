package com.caetp.digiex.controller.cms;

import com.caetp.digiex.controller.BaseController;
import com.caetp.digiex.response.Response;
import com.caetp.digiex.response.TResponse;
import com.caetp.digiex.service.CommonService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;


@Api(tags = "上传文件通用")
@RestController
@RequestMapping("/cms/common")
public class CommonController extends BaseController {

    @Autowired
    private CommonService commonService;

    @ApiOperation(value = "上传文件", notes = "上传文件",response = String.class)
    @PostMapping("/upload")
    public Response uploadFile(
            @ApiParam(value = "文件", required = true) MultipartFile file
    ) {

        return TResponse.success(commonService.uploadFile(file));
    }
}
