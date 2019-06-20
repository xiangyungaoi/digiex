package com.caetp.digiex.controller.api;

import com.caetp.digiex.controller.BaseController;
import com.caetp.digiex.dto.api.MarketDTO;
import com.caetp.digiex.entity.Market;
import com.caetp.digiex.response.Response;
import com.caetp.digiex.response.TResponse;
import com.caetp.digiex.service.MarketService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by wangzy on 2019/2/24.
 */
@Api(tags = "行情-API")
@RestController
@RequestMapping("/api/market")
public class MarketController extends BaseController {

    @Autowired
    private MarketService marketService;

    @ApiOperation(value="行情列表-wzy",response = MarketDTO.class, responseContainer = "List")
    @PostMapping("/list")
    public Response list(
            @ApiParam(name = "token",value = "token") @RequestParam(value = "token", required = false) String token,
            @ApiParam(name = "pageNumber", value = "页码, 1 基") @RequestParam(defaultValue = "1") Integer pageNumber,
            @ApiParam(name = "pageSize", value = "页大小,默认15") @RequestParam(defaultValue = "15") Integer pageSize,
            @ApiParam(name = "type", value = "类型：外汇：fx", required = true, allowableValues = "fx") @RequestParam() String type
    ) {
        return TResponse.success(marketService.list(token, type, pageNumber, pageSize));
    }

    @ApiOperation(value="自选-wzy",response = MarketDTO.class, responseContainer = "List")
    @PostMapping("/collectList")
    public Response collectList(
            @ApiParam(name = "token",value = "token") @RequestParam(value = "token", required = false) String token,
            @ApiParam(name = "pageNumber", value = "页码, 1 基") @RequestParam(defaultValue = "1") Integer pageNumber,
            @ApiParam(name = "pageSize", value = "页大小,默认15") @RequestParam(defaultValue = "15") Integer pageSize
    ) {
        return TResponse.success(marketService.collectList(token, pageNumber, pageSize));
    }

    @ApiOperation(value="收藏-wzy",response = Response.class)
    @PostMapping("/collect")
    public Response collect(
            @ApiParam(name = "token",value = "token", required = true) @RequestParam String token,
            @ApiParam(name = "id", value = "行情中的币种id") @RequestParam Long id
    ) {
        marketService.collect(token, id);
        return TResponse.SUCCESS;
    }

    @ApiOperation(value="取消收藏-wzy",response = Response.class)
    @PostMapping("/cancelCollect")
    public Response cancelCollect(
            @ApiParam(name = "token",value = "token", required = true) @RequestParam String token,
            @ApiParam(name = "id", value = "行情中的币种id") @RequestParam Long id
    ) {
        marketService.cancelCollect(token, id);
        return TResponse.SUCCESS;
    }

    @ApiOperation(value="搜索-gxy",response = MarketDTO.class, responseContainer = "List")
    @PostMapping("/searchCollect")
    public Response searchCollect(
            @ApiParam(name = "token",value = "token") @RequestParam(value = "token", required = false) String token,
            @ApiParam(name = "pageNumber", value = "页码, 1 基") @RequestParam(defaultValue = "1") Integer pageNumber,
            @ApiParam(name = "pageSize", value = "页大小,默认15") @RequestParam(defaultValue = "15") Integer pageSize,
            @ApiParam(name = "keyword", value = "关键字", required = true) @RequestParam() String keyword
    ){
        return TResponse.success(marketService.searchCollect(token, keyword, pageNumber, pageSize));
    }


}
