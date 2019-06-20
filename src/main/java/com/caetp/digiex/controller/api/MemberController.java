package com.caetp.digiex.controller.api;

import com.caetp.digiex.controller.BaseController;
import com.caetp.digiex.dto.api.AreaCodeDTO;
import com.caetp.digiex.dto.api.MemberDetailDTO;
import com.caetp.digiex.response.Response;
import com.caetp.digiex.response.TResponse;
import com.caetp.digiex.service.MemberService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by wangzy on 2018/11/10.
 */
@Api(tags = "用户-API")
@RestController
@RequestMapping("/api/user")
public class MemberController extends BaseController {

    @Autowired
    private MemberService memberService;


    @ApiOperation(value="获取手机区号列表-wzy",response = AreaCodeDTO.class)
    @PostMapping("/areaCodeList")
    public Response getAreaCodeList(){
        return TResponse.success(memberService.getAreaCodeList());
    }

    @ApiOperation(value="用户登录-wzy", response = MemberDetailDTO.class)
    @PostMapping("/login")
    public Response login(
            @ApiParam(name = "areaCode",value = "手机区号", required = true) @RequestParam("areaCode") String areaCode,
            @ApiParam(name = "mobile",value = "手机号码", required = true) @RequestParam("mobile") String mobile,
            @ApiParam(name = "password",value = "密码", required = true) @RequestParam("password") String password
    ) {
        return TResponse.success(memberService.login(areaCode, mobile, password));
    }

    @ApiOperation(value="用户详情-wzy", response = MemberDetailDTO.class)
    @PostMapping("/detail")
    public Response detail(
            @ApiParam(name = "token",value = "token", required = true) @RequestParam("token") String token
    ) {
        return TResponse.success(memberService.detail(token));
    }

}
