package com.caetp.digiex.controller.api;

import com.caetp.digiex.controller.BaseController;
import com.caetp.digiex.dto.api.AppVersionsDTO;
import com.caetp.digiex.response.Response;
import com.caetp.digiex.response.TResponse;
import com.caetp.digiex.service.AppVersionsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by wangzy on 2018/11/26.
 */
@Api(tags = "App版本-API")
@RestController
@RequestMapping("/api/appVersion")
public class AppVersionsController extends BaseController {

    @Autowired
    private AppVersionsService appVersionsService;

    @ApiOperation(value="最新版本-wzy",response = AppVersionsDTO.class)
    @PostMapping("/last")
    public Response getLastVersion(
            @ApiParam(name = "apiVersion",value = "版本编号", required = true) @RequestParam("apiVersion") String apiVersion,
            @ApiParam(name = "deviceType",value = "设备类型", required = true, allowableValues = "Android,IOS") @RequestParam("deviceType") String deviceType
    ) {
        AppVersionsDTO versionsDTO = appVersionsService.getLastVersion(apiVersion, deviceType);
        return TResponse.success(versionsDTO);
    }

}
