package com.caetp.digiex.entity.mapper;

import com.caetp.digiex.dto.api.TransactionRecordDTO;
import com.caetp.digiex.entity.Mt5UserOrder;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface Mt5UserOrderMapper {
    int deleteByPrimaryKey(@Param("mt5OrderId") String mt5OrderId, @Param("userOrderId") Long userOrderId);

    int insert(Mt5UserOrder record);

    int insertSelective(Mt5UserOrder record);

    Mt5UserOrder selectByPrimaryKey(@Param("mt5OrderId") String mt5OrderId, @Param("userOrderId") Long userOrderId);

    int updateByPrimaryKeySelective(Mt5UserOrder record);

    int updateByPrimaryKey(Mt5UserOrder record);

    /**
     * 查询交易记录
     * @param memberId
     * @param userOrderId
     * @param orderType
     * @return
     */
    List<TransactionRecordDTO> transactionRecordList(@Param("memberId")Integer memberId, @Param("userOrderId")Long userOrderId,
                                                         @Param("orderType")String orderType);

    /**
     * @param id mt5订单id
     * @return  该mt5订单交易的次数
     */
    Integer countAiTradeById(Long id);

}