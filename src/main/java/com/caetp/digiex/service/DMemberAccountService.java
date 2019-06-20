package com.caetp.digiex.service;

import com.caetp.digiex.dto.api.DmemberAccount;
import com.caetp.digiex.entity.DMemberAccount;
import com.caetp.digiex.entity.Member;
import com.caetp.digiex.entity.rmsmapper.DMemberAccountMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * Created by gaoyx on 2019/5/16.
 */

@Service
public class DMemberAccountService extends  BaseService{

    @Autowired
    DMemberAccountMapper dMemberAccountMapper;

    public DmemberAccount getUserUsdAmount(String token) {
        Member member = validateUserToken(token);
        DMemberAccount dMemberAccount = dMemberAccountMapper.getUserUsdAmount(member.getMemberId().toString());
        Long usdBalanceInt= dMemberAccount.getUsdBalance();
        Double usdBalanceDouble = new BigDecimal(usdBalanceInt.toString())
                .divide(new BigDecimal("100.00")).doubleValue();
        return new DmemberAccount(usdBalanceDouble.toString());
    }
}
