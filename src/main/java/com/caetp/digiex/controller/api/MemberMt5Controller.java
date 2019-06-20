package com.caetp.digiex.controller.api;

import com.caetp.digiex.dto.api.MemberMt5DTO;
import com.caetp.digiex.entity.mapper.MemberMt5Mapper;
import com.caetp.digiex.response.Response;
import com.caetp.digiex.response.TResponse;
import com.caetp.digiex.service.MemberMt5Service;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by gaoyx on 2019/5/24.
 */
@Api(tags = "mt5账户-api")
@RestController
@RequestMapping("/api/membermt5")
public class MemberMt5Controller {

    @Autowired
    private MemberMt5Service memberMt5Service;

    @ApiOperation(value = "查看用户当前杠杆率", response = MemberMt5DTO.class)
    @PostMapping("/getUserLeverage")
    public Response getUserLeverage(
            @ApiParam(name = "token", value = "token", required = true) @RequestParam("token") String token
    ) {
        return TResponse.success(memberMt5Service.getUserLeverage(token));
    }

    @ApiOperation(value = "查看用户的mt5账户信息", response = MemberMt5DTO.class)
    @PostMapping("/getUserMt5Info")
    public Response getUserMt5Info(
            @ApiParam(name = "token", value = "token", required = true) @RequestParam("token") String token
    ) {
        return TResponse.success(memberMt5Service.getUserMt5Info(token));
    }

    @ApiOperation(value = "用户注册mt5账号", response = Response.class )
    @PostMapping("/registerMt5")
    public Response registerMt5(
            @ApiParam(name = "token", value = "token", required = true) @RequestParam("token") String token
    ){
        memberMt5Service.registerMt5(token);
        return TResponse.SUCCESS;
    }

}
