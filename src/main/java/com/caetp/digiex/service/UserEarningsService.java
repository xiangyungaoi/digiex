package com.caetp.digiex.service;

import com.caetp.digiex.dto.PageInfoDTO;
import com.caetp.digiex.dto.TPage;
import com.caetp.digiex.dto.api.EarningsDTO;
import com.caetp.digiex.dto.api.TransactionRecordDTO;
import com.caetp.digiex.entity.Member;
import com.caetp.digiex.entity.mapper.UserEarningsMapper;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class UserEarningsService extends BaseService{

    @Autowired
    private UserEarningsMapper userEarningsMapper;

    public HashMap<String,Object> userEarnings(Integer pageNumber, Integer pageSize, String token) {

        Member member = validateUserToken(token);

        //查询每日收益
        Page<EarningsDTO> userEarningsList = PageHelper.startPage(pageNumber, pageSize).doSelectPage(() -> userEarningsMapper.userEarningsList(member.getMemberId()));

        PageInfo<EarningsDTO> pageInfo = new PageInfo<>(userEarningsList);
        PageInfoDTO pageInfoDTO = new PageInfoDTO((int) pageInfo.getTotal(), pageNumber, pageInfo.getPages(), pageSize);

        //查询总收益
        Double totalEarnings = userEarningsMapper.selectTotalEarnings(member.getMemberId());

        HashMap<String, Object> map = new HashMap<>();
        map.put("totalEarnings", totalEarnings);
        map.put("list", userEarningsList);
        map.put("pageInfo", pageInfoDTO);
        map.put("total", pageInfo.getTotal());

        return map;
    }
}
