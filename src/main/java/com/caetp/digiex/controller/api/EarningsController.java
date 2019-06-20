package com.caetp.digiex.controller.api;

import com.caetp.digiex.dto.api.EarningsDTO;
import com.caetp.digiex.dto.api.EarningsListDTO;
import com.caetp.digiex.dto.api.TransactionRecordDTO;
import com.caetp.digiex.response.Response;
import com.caetp.digiex.response.TResponse;
import com.caetp.digiex.service.UserEarningsService;
import com.caetp.digiex.service.UserOrderEarningsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * Created by zsd on 2019/3/21.
 */
@Api(tags = "每日收益-API")
@RestController
@RequestMapping("/api/userEarnings")
public class EarningsController {

    @Autowired
    private UserOrderEarningsService userOrderEarningsService;

    @Autowired
    private UserEarningsService userEarningsService;



    @ApiOperation(value="用户订单日收益-zsd", response = EarningsListDTO.class)
    @PostMapping("/orderEarnings")
    public Response orderEarnings(
            @ApiParam(name = "userOrderId", value = "用户订单表的id", required = true) @RequestParam(value = "userOrderId") Long userOrderId,
            @ApiParam("页码, 1 基") @RequestParam(defaultValue = "1") Integer pageNumber,
            @ApiParam("页大小,默认20") @RequestParam(defaultValue = "20") Integer pageSize,
            @ApiParam(name = "token",value = "token", required = true) @RequestParam("token") String token
    ){
        return TResponse.success(userOrderEarningsService.userOrderEarnings(userOrderId, pageNumber, pageSize, token));
    }

    @ApiOperation(value="用户日收益-zsd", response = EarningsListDTO.class)
    @PostMapping("/mt5TransactionList")
    public Response mt5TransactionList(
            @ApiParam(name = "token",value = "token", required = true) @RequestParam("token") String token,
            @ApiParam("页码, 1 基") @RequestParam(defaultValue = "1") Integer pageNumber,
            @ApiParam("页大小,默认20") @RequestParam(defaultValue = "20") Integer pageSize
    ){
        return TResponse.success(userEarningsService.userEarnings(pageNumber, pageSize, token));
    }
}
