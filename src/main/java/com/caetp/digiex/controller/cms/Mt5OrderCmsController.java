package com.caetp.digiex.controller.cms;


import com.caetp.digiex.dto.TPage;
import com.caetp.digiex.dto.cms.Mt5OrderCmsDto;
import com.caetp.digiex.dto.cms.Mt5OrderDetailCmsDto;
import com.caetp.digiex.dto.cms.UserOrderCmsDto;
import com.caetp.digiex.response.Response;
import com.caetp.digiex.response.TResponse;
import com.caetp.digiex.service.Mt5OrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Api(tags = "MT5订单-cms")
@RestController
@RequestMapping("/cms/aiRobot")
public class Mt5OrderCmsController {

    @Autowired
    Mt5OrderService mt5OrderService;

    @ApiOperation(value = "查询MT5未完成订单-sjy", response = Mt5OrderCmsDto.class)
    @GetMapping("/selectMt5UndoneList")
    public Response selectMt5UndoneList(
            @ApiParam("页码, 1 基") @RequestParam(defaultValue = "1") Integer pageNumber,
            @ApiParam("页大小,默认15") @RequestParam(defaultValue = "15") Integer pageSize,
            @ApiParam("ai机器人id") @RequestParam Long aiRobotId) {
        return TResponse.success(mt5OrderService.selectMt5UndoneList(pageNumber, pageSize, aiRobotId));
    }

    @ApiOperation(value = "查询MT5持仓订单-sjy", response = Mt5OrderCmsDto.class)
    @GetMapping("/selectMt5PositionList")
    public Response selectMt5PositionList(
            @ApiParam("页码, 1 基") @RequestParam(defaultValue = "1") Integer pageNumber,
            @ApiParam("页大小,默认15") @RequestParam(defaultValue = "15") Integer pageSize,
            @ApiParam("ai机器人id") @RequestParam Long aiRobotId) {
        return TResponse.success(mt5OrderService.selectMt5PositionList(pageNumber, pageSize, aiRobotId));
    }

    @ApiOperation(value = "查询MT5平仓订单-sjy", response = Mt5OrderCmsDto.class)
    @GetMapping("/selectMt5EveningUpList")
    public Response selectMt5EveningUpList(
            @ApiParam("页码, 1 基") @RequestParam(defaultValue = "1") Integer pageNumber,
            @ApiParam("页大小,默认15") @RequestParam(defaultValue = "15") Integer pageSize,
            @ApiParam("ai机器人id") @RequestParam Long aiRobotId) {
        return TResponse.success(mt5OrderService.selectMt5EveningUpList(pageNumber, pageSize, aiRobotId));
    }

    @ApiOperation(value = "MT5持仓或者平仓订单详情-sjy", response = Mt5OrderDetailCmsDto.class)
    @GetMapping("/mt5OrderDetail")
    public Response mt5OrderDetail(
            @ApiParam("订单号") @RequestParam String orderId) {
        return TResponse.success(mt5OrderService.mt5OrderDetail(orderId));
    }

    @ApiOperation(value = "未建仓MT5订单的用户订单列表-sjy", response = UserOrderCmsDto.class)
    @GetMapping("/selectMt5ByUndoneInfoView")
    public Response selectMt5ByUndoneInfoView(
            @ApiParam("页码, 1 基") @RequestParam(defaultValue = "1") Integer pageNumber,
            @ApiParam("页大小,默认15") @RequestParam(defaultValue = "15") Integer pageSize,
            @ApiParam("ai机器人id") @RequestParam Long aiRobotId) {
        return TResponse.success(mt5OrderService.selectMt5ByUndoneInfoView(pageNumber, pageSize, aiRobotId));
    }

    @ApiOperation(value = "持仓或平仓MT5订单的用户订单列表-sjy", response = UserOrderCmsDto.class)
    @GetMapping("/userOrdersByMT5OrderId")
    public Response userOrdersByMT5OrderId(
            @ApiParam("页码, 1 基") @RequestParam(defaultValue = "1") Integer pageNumber,
            @ApiParam("页大小,默认15") @RequestParam(defaultValue = "15") Integer pageSize,
            @ApiParam("MT5订单id") @RequestParam String orderId) {
        return TResponse.success( mt5OrderService.userOrdersByMT5OrderId(pageNumber, pageSize, orderId));
    }

    @ApiOperation(value = "建仓-sjy", response = Response.class)
    @GetMapping("/updateMt5UndoneOrder")
    public Response updateMt5UndoneOrder(
            @ApiParam("ai机器人id") @RequestParam Long aiRobotId,
            @ApiParam("方向") @RequestParam String orderType,
            @ApiParam("币种") @RequestParam String aiType,
            @ApiParam("建仓时间") @RequestParam String buyTime,
            @ApiParam("建仓点数") @RequestParam Double buyPrice,
            @ApiParam("建仓手续费") @RequestParam Double buyServiceFee

    ) {
        mt5OrderService.updateMt5UndoneOrder(aiRobotId, orderType, aiType, buyTime, buyPrice, buyServiceFee);
        return TResponse.SUCCESS;
    }

    @ApiOperation(value = "平仓-wzy", response = Response.class)
    @GetMapping("/updateMt5OrderAndPosition")
    public Response updateMt5OrderAndPosition(
            @ApiParam("MT5订单id") @RequestParam String orderId,
            @ApiParam("平仓时间") @RequestParam String sellTime,
            @ApiParam("平仓点数") @RequestParam Double sellPrice,
            @ApiParam("平仓手续费") @RequestParam Double sellServiceFee,
            @ApiParam("库存费") @RequestParam Double inventoryFee,
            @ApiParam("总盈亏") @RequestParam Double totalEarnings
    ) {
        mt5OrderService.updateMt5OrderAndPosition(orderId, sellTime, sellPrice, sellServiceFee,
                inventoryFee, totalEarnings);
        return TResponse.SUCCESS;
    }

}
