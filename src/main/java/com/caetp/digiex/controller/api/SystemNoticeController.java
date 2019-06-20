package com.caetp.digiex.controller.api;

import com.caetp.digiex.dto.api.SystemNoticeDetailDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.caetp.digiex.controller.BaseController;
import com.caetp.digiex.response.Response;
import com.caetp.digiex.response.TResponse;
import com.caetp.digiex.service.SystemNoticeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * Created by wzy on 2018/12/29.
 */
@Api(tags = "公告-API")
@RestController
@RequestMapping("/api/notice")
public class SystemNoticeController extends BaseController {
	
	@Autowired
	private SystemNoticeService systemNoticeService;
	
	@ApiOperation(value="是否有弹框推送-wzy",response = SystemNoticeDetailDTO.class)
    @PostMapping("/isPopup")
    public Response isPopup() {
        return TResponse.success(systemNoticeService.isPopup());
    }
}
