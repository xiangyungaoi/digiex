package com.caetp.digiex.service;

import com.caetp.digiex.dto.api.EarningsOrderDTO;
import com.caetp.digiex.dto.api.EarningsUserDTO;
import com.caetp.digiex.entity.UserEarnings;
import com.caetp.digiex.entity.UserOrderEarnings;
import com.caetp.digiex.entity.mapper.UserEarningsMapper;
import com.caetp.digiex.entity.mapper.UserOrderEarningsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by wangzy on 2019/3/25.
 */
@Service
public class EarningsService {

    @Autowired
    private UserOrderEarningsMapper userOrderEarningsMapper;
    @Autowired
    private UserEarningsMapper userEarningsMapper;

    /**
     * 计算订单前一日日收益
     */
    public void calculateOrderEarnings(LocalDateTime dateTime) {
        UserOrderEarnings earnings = UserOrderEarnings.builder().build();
        List<EarningsOrderDTO> list = userOrderEarningsMapper.orderEarnings(dateTime.plusDays(-1));
        for (EarningsOrderDTO dto : list) {
            earnings.setUserOrderId(dto.getUserOrderId());
            earnings.setEarnings(dto.getEarnings());
            earnings.setMemberId(dto.getMemberId());
            earnings.setCreatedTime(dateTime.plusDays(-1));
            userOrderEarningsMapper.insertSelective(earnings);
        }
    }

    /**
     * 计算用户前一日日收益
     */
    public void calculateUserEarnings(LocalDateTime dateTime) {
        UserEarnings earnings = UserEarnings.builder().build();
        List<EarningsUserDTO> list = userEarningsMapper.userEarnings(dateTime.plusDays(-1));
        for (EarningsUserDTO dto : list) {
            earnings.setEarnings(dto.getEarnings());
            earnings.setMemberId(dto.getMemberId());
            earnings.setCreatedTime(dateTime.plusDays(-1));
            userEarningsMapper.insertSelective(earnings);
        }
    }
}
