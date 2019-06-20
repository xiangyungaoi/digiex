package com.caetp.digiex.controller.api;

import com.caetp.digiex.dto.api.DmemberAccount;
import com.caetp.digiex.response.Response;
import com.caetp.digiex.response.TResponse;
import com.caetp.digiex.service.DMemberAccountService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by gaoyx on 2019/5/16.
 */
@Api(tags = "数市会员账户-api")
@RestController
@RequestMapping("/api/dmemberaccount")
public class DMemberAccountController {

    @Autowired
    DMemberAccountService dMemberAccountService;

    @ApiOperation(value = "查看用户数市会员账户的USD货币余额", response = DmemberAccount.class)
    @PostMapping("/getUserUsdAmount")
    public Response getUserUsdAmount(
            @ApiParam(name = "token", value = "token", required = true) @RequestParam("token") String token
    ) {
        return TResponse.success(dMemberAccountService.getUserUsdAmount(token));
    }

}
