package com.caetp.digiex.controller.cms;

import com.caetp.digiex.controller.BaseController;
import com.caetp.digiex.dto.cms.SysUserDTO;
import com.caetp.digiex.response.Response;
import com.caetp.digiex.response.TResponse;
import com.caetp.digiex.service.Mt5OrderService;
import com.caetp.digiex.service.SysUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author shijy
 */
@Api(tags = "用户登录-cms")
@RestController
@RequestMapping("/cms/user")
public class SysUserController extends BaseController {

    @Autowired
    SysUserService sysUserService;

    @Autowired
    Mt5OrderService mt5OrderService;

    @ApiOperation(value = "用户登录", response = SysUserDTO.class)
    @PostMapping("/login")
    public Response login(
            @ApiParam(name = "username",value = "用户名", required = true) @RequestParam("username") String username,
            @ApiParam(name = "password",value = "用户密码", required = true) @RequestParam("password") String password

    ) {
        SysUserDTO login = sysUserService.toLogin(request,username, password);
        return TResponse.success(login);
    }

    @ApiOperation(value = "退出登陆",response = TResponse.class)
    @GetMapping("/login/logout")
    public Response logout() {
        sysUserService.logout(request);
        return TResponse.SUCCESS;
    }

    @ApiOperation(value = "用户信息",response = SysUserDTO.class)
    @GetMapping("/userDetail")
    public Response userDetail() {
        SysUserDTO sysUserDTOList =  sysUserService.userDetail(request);
        return TResponse.success(sysUserDTOList);
    }
}
