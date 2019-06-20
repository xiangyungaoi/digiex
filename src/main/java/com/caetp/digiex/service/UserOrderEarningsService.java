package com.caetp.digiex.service;

import com.caetp.digiex.dto.PageInfoDTO;
import com.caetp.digiex.dto.api.EarningsDTO;
import com.caetp.digiex.entity.Member;
import com.caetp.digiex.entity.mapper.UserOrderEarningsMapper;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;


@Service
public class UserOrderEarningsService extends BaseService{

    @Autowired
    private UserOrderEarningsMapper userOrderEarningsMapper;

    public HashMap<String, Object> userOrderEarnings(Long userOrderId, Integer pageNumber, Integer pageSize, String token) {
        Member member = validateUserToken(token);

        //查询每日收益
        Page<EarningsDTO> userOrderEarningsList = PageHelper.startPage(pageNumber, pageSize).doSelectPage(() -> userOrderEarningsMapper.userOrderEarningsList(userOrderId,member.getMemberId()));

        PageInfo<EarningsDTO> pageInfo = new PageInfo<>(userOrderEarningsList);
        PageInfoDTO pageInfoDTO = new PageInfoDTO((int) pageInfo.getTotal(), pageNumber, pageInfo.getPages(), pageSize);

        //查询用户订单所在AI的总收益
        Double totalEarnings = userOrderEarningsMapper.selectTotalEarnings(userOrderId,member.getMemberId());

        HashMap<String, Object> map = new HashMap<>();
        map.put("totalEarnings", totalEarnings);
        map.put("list", userOrderEarningsList);
        map.put("pageInfo", pageInfoDTO);
        map.put("total", pageInfo.getTotal());
        return map;
    }
}
