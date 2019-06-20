package com.caetp.digiex.controller.api;

import com.caetp.digiex.controller.BaseController;
import com.caetp.digiex.dto.api.UserHistoeryOrderDTO;
import com.caetp.digiex.dto.api.UserOrderDetailDTO;
import com.caetp.digiex.dto.api.UserOrderListDTO;
import com.caetp.digiex.response.Response;
import com.caetp.digiex.response.TResponse;
import com.caetp.digiex.service.UserOrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by hy on 2018/2/17.
 */
@Api(tags = "订单_1.1.2-API")
@RestController
@RequestMapping("/api/userOrder")
public class UserOrderController extends BaseController {

    @Autowired
    private UserOrderService userOrderService;

    @ApiOperation(value="持仓列表-wzy",response = UserOrderListDTO.class)
    @PostMapping("/userOrderBuyList")
    public Response userOrderBuyList(
            @ApiParam("页码, 1 基") @RequestParam(defaultValue = "1") Integer pageNumber,
            @ApiParam("页大小,默认10") @RequestParam(defaultValue = "10") Integer pageSize,
            @ApiParam(name = "token",value = "token", required = true) @RequestParam(value = "token") String token
    ) {
        return TResponse.success(userOrderService.userOrderBuyList(pageNumber, pageSize, token));
    }

    @ApiOperation(value = "订单详情_1.1.2-gxy",response = UserOrderDetailDTO.class)
    @PostMapping("/userOrderDetail")
    public Response userOrderBuyDetail(
            @ApiParam(name = "token",value = "token", required = true) @RequestParam(value = "token") String token,
            @ApiParam(name = "id",value = "id", required = true) @RequestParam(value = "id") Long id
    ){
        return TResponse.success(userOrderService.userOrderDetail(token, id));
    }

    @ApiOperation(value="历史列表-wzy",response = UserOrderListDTO.class)
    @PostMapping("/userOrderSellList")
    public Response userOrderSellList(
            @ApiParam("页码, 1 基") @RequestParam(defaultValue = "1") Integer pageNumber,
            @ApiParam("页大小,默认15") @RequestParam(defaultValue = "15") Integer pageSize,
            @ApiParam(name = "token",value = "token", required = true) @RequestParam(value = "token") String token
    ) {
        return TResponse.success(userOrderService.userOrderSellList(pageNumber, pageSize, token));
    }

    @ApiOperation(value = "撤销未建仓的订单-wzy", response = TResponse.class)
    @PostMapping("/cancelUserOrder")
    public Response cancelUserOrder(
            @ApiParam(name = "token",value = "token", required = true) @RequestParam("token") String token,
            @ApiParam(name = "id",value = "id", required = true) @RequestParam(value = "id") Long id
    ) {
        userOrderService.cancelUserOrder(token, id);
        return TResponse.SUCCESS;
    }

    @ApiOperation(value = "删除历史订单-wzy", response = TResponse.class)
    @PostMapping("/deleteUserOrderSell")
    public Response deleteUserOrder(
            @ApiParam(name = "token",value = "token", required = true) @RequestParam("token") String token,
            @ApiParam(name = "id",value = "id", required = true) @RequestParam(value = "id") Long id
    ) {
        userOrderService.deleteUserOrderSell(token, id);
        return TResponse.SUCCESS;
    }

    @ApiOperation(value = "用户订单平仓-wzy", response = TResponse.class)
    @PostMapping("/sellUserOrder")
    public Response sellUserOrder(
            @ApiParam(name = "token",value = "token", required = true) @RequestParam("token") String token,
            @ApiParam(name = "id",value = "id", required = true) @RequestParam(value = "id") Long id
    ) {
        userOrderService.sellUserOrder(token, id);
        return TResponse.SUCCESS;
    }

    @ApiOperation(value="订单历史列表-gxy-1.1.3",response = UserHistoeryOrderDTO.class)
    @PostMapping("/userOrderHistoeryList")
    public Response userOrderHistoeryList(
            @ApiParam("页码, 1 基") @RequestParam(defaultValue = "1") Integer pageNumber,
            @ApiParam("页大小,默认5") @RequestParam(defaultValue = "5") Integer pageSize,
            @ApiParam(name = "token",value = "token", required = true) @RequestParam(value = "token") String token
    ) {
        return TResponse.success(userOrderService.userOrderHistoeryList(pageNumber, pageSize, token));
    }

}
