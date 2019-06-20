package com.caetp.digiex.controller.api;

import com.caetp.digiex.controller.BaseController;
import com.caetp.digiex.dto.TPage;
import com.caetp.digiex.dto.api.AiRobotDetailDTO;
import com.caetp.digiex.dto.api.AiRobotListDTO;
import com.caetp.digiex.response.Response;
import com.caetp.digiex.response.TResponse;
import com.caetp.digiex.service.AiRobotService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by hy on 2018/2/17.
 */
@Api(tags = "AI跟单_1.1.2-API")
@RestController
@RequestMapping("/api/aiRobot")
public class AiRobotController extends BaseController {

    @Autowired
    private AiRobotService aiRobotService;

    @ApiOperation(value="AI列表-wzy",response = AiRobotListDTO.class)
    @PostMapping("/list")
    public Response aiRobotList(
            @ApiParam(name = "token",value = "token", required = true) @RequestParam(value = "token") String token
    ) {
        return TResponse.success(aiRobotService.aiRobotList(token));
    }

    @ApiOperation(value = "AI详情-wzy",response = AiRobotDetailDTO.class)
    @PostMapping("/detail")
    public Response aiRobotDetail(
            @ApiParam(name = "token",value = "token", required = true) @RequestParam(value = "token") String token,
            @ApiParam(name = "id",value = "AI机器人id", required = true) @RequestParam(value = "id") Long id
    ){
        return TResponse.success(aiRobotService.aiRobotDetail(token, id));
    }

    @ApiOperation(value = "跟随AI交易_1.1.2_gxy", response = TResponse.class)
    @PostMapping("/transactionAi")
    public Response transactionAi(
            @ApiParam(name = "token",value = "token", required = true) @RequestParam("token") String token,
            @ApiParam(name = "id",value = "AI机器人id", required = true) @RequestParam(value = "id") Long id,
            @ApiParam(name = "totalStandardHands",value = "totalStandardHands", required = true) @RequestParam(value = "totalStandardHands") Double totalStandardHands,
            @ApiParam(name = "location", value = "位置信息,格式:xx省-xx市-xx区", required = true) @RequestParam(value = "location", defaultValue = "") String location
    ) {
        aiRobotService.transactionAi(token, id, totalStandardHands, location);
        return TResponse.SUCCESS;
    }
}
