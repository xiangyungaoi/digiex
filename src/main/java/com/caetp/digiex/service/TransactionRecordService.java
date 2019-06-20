package com.caetp.digiex.service;

import com.caetp.digiex.dto.PageInfoDTO;
import com.caetp.digiex.dto.TPage;
import com.caetp.digiex.dto.api.TransactionRecordDTO;
import com.caetp.digiex.entity.Member;
import com.caetp.digiex.entity.mapper.Mt5UserOrderMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionRecordService extends BaseService{

    @Autowired
    private Mt5UserOrderMapper mt5UserOrderMapper;

    /**
     * 查询交易记录列表
     * @param token
     * @param pageNumber
     * @param pageSize
     * @param userOrderId
     * @param orderType
     * @return
     */
    public TPage<TransactionRecordDTO> transactionRecordList(String token, Integer pageNumber, Integer pageSize, Long userOrderId, String orderType) {

        Member member = validateUserToken(token);

        List<TransactionRecordDTO> transactionRecordDTOList = PageHelper.startPage(pageNumber, pageSize).doSelectPage(() ->
                mt5UserOrderMapper.transactionRecordList(member.getMemberId(), userOrderId, orderType));

        PageInfo<TransactionRecordDTO> pageInfo = new PageInfo<>(transactionRecordDTOList);
        PageInfoDTO pageInfoDTO = new PageInfoDTO((int) pageInfo.getTotal(), pageNumber, pageInfo.getPages(), pageSize);
        return TPage.of(transactionRecordDTOList, pageInfo.getTotal(), pageInfoDTO);
    }
}
