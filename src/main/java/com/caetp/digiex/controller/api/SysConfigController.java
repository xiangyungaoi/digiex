package com.caetp.digiex.controller.api;

import com.caetp.digiex.controller.BaseController;
import com.caetp.digiex.dto.api.AiOrderConfigDTO;
import com.caetp.digiex.response.Response;
import com.caetp.digiex.response.TResponse;
import com.caetp.digiex.service.SysConfigService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by wangzy on 2019/3/21.
 */
@Api(tags = "系统配置-API")
@RestController
@RequestMapping("/api/config")
public class SysConfigController extends BaseController {

    @Autowired
    private SysConfigService sysConfigService;

    @ApiOperation(value="获取AI跟单系统配置信息_已废弃-wzy",response = AiOrderConfigDTO.class)
    @PostMapping("/aiOrder")
    public Response aiOrder(){
        return TResponse.success(sysConfigService.aiOrder());
    }
}
