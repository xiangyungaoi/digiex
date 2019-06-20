package com.caetp.digiex.controller.cms;

import com.caetp.digiex.controller.BaseController;
import com.caetp.digiex.dto.cms.AiFeatureDTO;
import com.caetp.digiex.response.Response;
import com.caetp.digiex.response.TResponse;
import com.caetp.digiex.service.AiFeatureService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by wangzy on 2019/4/28.
 */
@Api(tags = "AI标签管理_1.1.1-cms")
@RestController
@RequestMapping("/cms/aiFeature")
public class AiFeatureCmsController extends BaseController {

    @Autowired
    private AiFeatureService aiFeatureService;

    @ApiOperation(value = "AI标签列表_1.1.1-wzy", response = AiFeatureDTO.class)
    @GetMapping("/list")
    public Response list() {
        return TResponse.success( aiFeatureService.list());
    }

    @ApiOperation(value = "新增AI标签_1.1.1-wzy", response = Response.class)
    @PostMapping("/add")
    public Response add(
            @ApiParam(name = "feature",value = "AI标签", required = true) @RequestParam("feature") String feature
    ) {
       aiFeatureService.add(feature);
       return TResponse.SUCCESS;
    }

    @ApiOperation(value = "删除AI标签_1.1.1-wzy", response = Response.class)
    @PostMapping("/delete")
    public Response delete(
            @ApiParam(name = "id",value = "AI标签id", required = true) @RequestParam("id") Long id
    ) {
        aiFeatureService.delete(id);
        return TResponse.SUCCESS;
    }
}
