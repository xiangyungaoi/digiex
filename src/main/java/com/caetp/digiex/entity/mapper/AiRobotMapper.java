package com.caetp.digiex.entity.mapper;

import com.caetp.digiex.dto.api.AiRobotDetailDTO;
import com.caetp.digiex.dto.api.AiRobotListDTO;
import com.caetp.digiex.dto.cms.AIRobotCmsNameDTO;
import com.caetp.digiex.dto.cms.AiRobotCmsDto;
import com.caetp.digiex.entity.AiRobot;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AiRobotMapper {

    int deleteByPrimaryKey(Long id);

    int insert(AiRobot record);

    int insertSelective(AiRobot record);

    AiRobot selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(AiRobot record);

    int updateByPrimaryKey(AiRobot record);

    List<AiRobotListDTO> aiRobotList();

    AiRobotDetailDTO aiRobotDetail(Long id);

    /**
     * 查询ai管理列表数据
     * @return
     */
    List<AiRobot> selectByList();

    /**
     * 查询AI机器人详情
     * @param id
     * @return
     */
    AiRobotCmsDto aiRobotView(@Param("id") Long id);

    /**
     * 获取AI机器人的名称
     * @return
     */
    List<AIRobotCmsNameDTO> aiRobotByName();

    /**
     * 用户订单中是否有机器人
     * @param id
     * @return
     */
    int aiRobtCount(Long id);

    String getAiName(Long id);
}