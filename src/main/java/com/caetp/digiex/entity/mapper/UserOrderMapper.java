package com.caetp.digiex.entity.mapper;

import com.caetp.digiex.dto.api.UserHistoeryOrderDTO;
import com.caetp.digiex.dto.api.UserOrderDetailDTO;
import com.caetp.digiex.dto.api.UserOrderInfoCountDTO;
import com.caetp.digiex.dto.api.UserOrderListDTO;
import com.caetp.digiex.entity.UserOrder;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public interface UserOrderMapper {
    int deleteByPrimaryKey(Long id);

    int insert(UserOrder record);

    int insertSelective(UserOrder record);

    UserOrder selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(UserOrder record);

    int updateByPrimaryKey(UserOrder record);

    List<UserOrderListDTO> userOrderBuyList(Integer memberId);

    UserOrderDetailDTO userOrderDetail(@Param("memberId")Integer memberId, @Param("id")Long id);

    List<UserOrderListDTO> userOrderSellList(Integer memberId);

    int deleteUserOrderSell(@Param("memberId")Integer memberId, @Param("id")Long id);

    int cancelUserOrder(@Param("memberId")Integer memberId, @Param("id")Long id);

    UserOrderInfoCountDTO orderCount(Integer memberId);

    int userOrderToBuy(@Param("id")Long id, @Param("buyServiceFee")BigDecimal buyServiceFee,
                       @Param("buyTime") LocalDateTime buyTime);

    int sellUserOrder(@Param("memberId")Integer memberId, @Param("id")Long id);

    Long orderCountByMemberId(Integer memberId);

    List<UserOrder> userHistoryOrdre(Integer memberId);

}