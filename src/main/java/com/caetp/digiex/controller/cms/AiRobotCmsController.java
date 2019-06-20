package com.caetp.digiex.controller.cms;

import com.caetp.digiex.controller.BaseController;
import com.caetp.digiex.dto.TPage;
import com.caetp.digiex.dto.cms.AIRobotCmsNameDTO;
import com.caetp.digiex.dto.cms.AiRobotCmsDto;
import com.caetp.digiex.entity.AiRobot;
import com.caetp.digiex.response.Response;
import com.caetp.digiex.response.TResponse;
import com.caetp.digiex.service.AiRobotService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * author huzj
 */
@Api(tags = "AI管理_1.1.1-cms")
@RestController
@RequestMapping("/cms/aiRobot")
public class AiRobotCmsController extends BaseController {

    @Autowired
    AiRobotService aiRobotService;

    @ApiOperation(value = "查询ai管理列表-hzj", response = AiRobot.class)
    @GetMapping("/aiRobotInfo")
    public Response aiRobotInfo(
            @ApiParam("页码, 1 基") @RequestParam(defaultValue = "1") Integer pageNumber,
            @ApiParam("页大小,默认15") @RequestParam(defaultValue = "15") Integer pageSize
    ) {
        TPage<AiRobot> aiRobotList = aiRobotService.aiRobotInfo(pageNumber,pageSize);
        return TResponse.success(aiRobotList);
    }

    @ApiOperation(value = "新增AI机器人_1.1.1-wzy", response = Integer.class)
    @PostMapping("/addAiRobot")
    public Response addAiRobot(@RequestBody AiRobotCmsDto aiRobotCmsDto) {
        Integer count = aiRobotService.addAiRobot(aiRobotCmsDto);
        return TResponse.success(count);
    }

    @ApiOperation(value = "删除AI机器人-hzj", response = Integer.class)
    @GetMapping("/delAiRobot")
    public Response delAiRobot(@RequestParam("id") Long id) {
        Integer count = aiRobotService.delAiRobot(id);
        return TResponse.success(count);
    }

    @ApiOperation(value = "查询AI机器人详情-hzj", response = AiRobotCmsDto.class)
    @GetMapping("/aiRobotView")
    public Response aiRobotView(@RequestParam("id") Long id) {
        AiRobotCmsDto aiRobotCmsDto = aiRobotService.aiRobotView(id);
        return TResponse.success(aiRobotCmsDto);
    }

    @ApiOperation(value = "修改AI机器人_1.1.1-wzy", response = Integer.class)
    @PostMapping("/modifyRobotView")
    public Response modifyRobotView(@RequestBody AiRobotCmsDto aiRobotCmsDto) {
        Integer count = aiRobotService.modifyRobotView(aiRobotCmsDto);
        return TResponse.success(count);
    }


    @ApiOperation(value = "查询AI机器人名称-hzj", response = AiRobotCmsDto.class)
    @GetMapping("/RobotByName")
    public Response RobotByName() {
        List<AIRobotCmsNameDTO> aiRobotCmsNameDTOList = aiRobotService.aiRobotByName();
        return TResponse.success(aiRobotCmsNameDTOList);
    }

}
