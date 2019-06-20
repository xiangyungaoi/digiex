package com.caetp.digiex.entity.mapper;

import com.caetp.digiex.dto.cms.Mt5OrderCmsDto;
import com.caetp.digiex.dto.cms.Mt5OrderDetailCmsDto;
import com.caetp.digiex.dto.cms.UserOrderCmsDto;
import com.caetp.digiex.dto.cms.UserOrderMT5CmsDto;
import com.caetp.digiex.entity.Mt5Order;
import com.caetp.digiex.entity.UserOrder;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public interface Mt5OrderMapper {
    int deleteByPrimaryKey(String orderId);

    int insert(Mt5Order record);

    int insertSelective(Mt5Order record);

    Mt5Order selectByPrimaryKey(String orderId);

    int updateByPrimaryKeySelective(Mt5Order record);

    int updateByPrimaryKey(Mt5Order record);

    int updateMt5OrderByPrimaryKeySelective(Mt5Order mt5Order);


    /**
     * 持仓订单列表
     */
    List<Mt5OrderCmsDto> selectMt5PositionList(Long aiRobotId);

    /**
     * 平仓订单列表
     */
    List<Mt5OrderCmsDto> selectMt5EveningUpList(Long aiRobotId);


    /**
     * 用户订单表
     */
    List<UserOrderCmsDto> selectMt5ByUndoneInfoView(@Param("aiRobotId") Long aiRobotId, @Param("nowDate")LocalDateTime nowDate);

    Mt5OrderDetailCmsDto mt5OrderDetail(String orderId);

    List<UserOrderCmsDto> userOrdersByMT5OrderId(String orderId);

    List<UserOrderMT5CmsDto> mt5UserOrderList(String orderId);

    int tradingMt5Orders(Long aiRobotId);

    int finishMt5Orders(@Param("aiRobotId") Long aiRobotId, @Param("nowDate") LocalDateTime nowDate);

    Mt5OrderCmsDto selectUndoneMt5Order(@Param("aiRobotId")Long aiRobotId, @Param("nowDate")LocalDateTime nowDate);

    int sellMt5Order(@Param("orderId")String orderId, @Param("sellPrice")BigDecimal sellPrice,
                     @Param("sellServiceFee")BigDecimal sellServiceFee, @Param("totalEarnings")BigDecimal totalEarnings,
                     @Param("sellTime") LocalDateTime sellTime,@Param("inventoryFee") BigDecimal inventoryFee);
}