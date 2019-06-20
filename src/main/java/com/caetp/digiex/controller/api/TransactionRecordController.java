package com.caetp.digiex.controller.api;

import com.caetp.digiex.dto.api.TransactionRecordDTO;
import com.caetp.digiex.dto.api.UserOrderListDTO;
import com.caetp.digiex.response.Response;
import com.caetp.digiex.response.TResponse;
import com.caetp.digiex.service.TransactionRecordService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by zsd on 2019/3/21.
 */
@Api(tags = "交易记录-API")
@RestController
@RequestMapping("/api/transactionRecord")
public class TransactionRecordController {

    @Autowired
    private TransactionRecordService transactionRecordService;


    @ApiOperation(value="查询交易记录列表-zsd", response = TransactionRecordDTO.class, responseContainer = "List")
    @PostMapping("/transactionRecordList")
    public Response userTransactionList(
            @ApiParam(name = "token",value = "token", required = true) @RequestParam("token") String token,
            @ApiParam(name = "userOrderId", value = "用户订单表的id，不传值代表查询用户所有订单的AI交易记录，传值代表查询当前订单所在的AI交易记录")
            @RequestParam(value = "userOrderId", required = false) Long userOrderId,
            @ApiParam("页码, 1 基") @RequestParam(defaultValue = "1") Integer pageNumber,
            @ApiParam("页大小,默认20") @RequestParam(defaultValue = "20") Integer pageSize,
            @ApiParam(name = "orderType", value = "订单类型：'all'所有,'buy'买入,'sell'卖出",
                    allowableValues = "buy,sell,all", defaultValue = "all", required = true)
            @RequestParam(value = "orderType") String orderType
    ){
        return TResponse.success(transactionRecordService.transactionRecordList(token, pageNumber, pageSize, userOrderId, orderType));
    }


}
