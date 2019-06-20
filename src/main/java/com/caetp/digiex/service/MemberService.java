package com.caetp.digiex.service;

import com.caetp.digiex.dto.TPage;
import com.caetp.digiex.dto.api.AreaCodeDTO;
import com.caetp.digiex.dto.api.MemberDetailDTO;
import com.caetp.digiex.dto.api.UserOrderInfoCountDTO;
import com.caetp.digiex.entity.DMember;
import com.caetp.digiex.entity.DMemberAccount;
import com.caetp.digiex.entity.Member;
import com.caetp.digiex.entity.mapper.AreaCodeMapper;
import com.caetp.digiex.entity.mapper.MemberMapper;
import com.caetp.digiex.entity.mapper.UserOrderMapper;
import com.caetp.digiex.entity.rmsmapper.DMemberAccountMapper;
import com.caetp.digiex.entity.rmsmapper.DMemberMapper;
import com.caetp.digiex.exception.UserException;
import com.caetp.digiex.utli.encryption.MD5;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by wangzy on 2018/11/10.
 */
@Service
public class MemberService extends BaseService {

    @Autowired
    private AreaCodeMapper areaCodeMapper;
    @Autowired
    private DMemberMapper dMemberMapper;
    @Autowired
    private MemberMapper memberMapper;
    @Autowired
    private UserOrderMapper userOrderMapper;
    @Autowired
    private DMemberAccountMapper dMemberAccountMapper;
    @Autowired
    Mt5HandleService mt5HandleService;

    /**
     * 获取手机区号列表
     * @return
     */
    public TPage<AreaCodeDTO> getAreaCodeList() {
        List<AreaCodeDTO> list = areaCodeMapper.getAreaCodeList();
        return TPage.of(list, list.size());
    }

    /**
     * 用户登录
     * @param areaCode
     * @param mobile
     * @param password
     * @return
     */
    public MemberDetailDTO login(String areaCode, String mobile, String password) {
        DMember dMember = dMemberMapper.selectByMobile(areaCode, mobile);
        Member member;
        if (dMember == null) {
            throw UserException.WRONG_USERNAME_OR_PASSWORD;
        }
        // VIP及以上身份的用户才能登录---V1.1.1版时取消身份限制
        /*if (dMember.getMemberLevel() == 0) {
            throw UserException.USER_LOGIN_PERMISSION_DENIED;
        }*/
        // 判断是否冻结
        if (!dMember.getAccountStatus()) {
            throw UserException.USER_FROZEN;
        }
        // 查询交易所用户是否已同步，不同步则插入
        member = memberMapper.selectByPrimaryKey(dMember.getMemberId());
        if (member == null) {
            // 插入同步
            member = Member.builder()
                    .memberId(dMember.getMemberId()).nickName(dMember.getNickName())
                    .areaCode(dMember.getAreaCode()).mobile(dMember.getMobile())
                    .isLocked(false).errorTimes(0)
                    .createdBy(dMember.getMemberId()).createdTime(LocalDateTime.now())
                    .updatedBy(dMember.getMemberId()).updatedTime(LocalDateTime.now())
                    .build();
            memberMapper.insertSelective(member);
        }

        Member editMember = Member.builder().memberId(member.getMemberId()).build();
        // 判断是否被锁
        if (member.getIsLocked()) {
            if (LocalDateTime.now().isBefore(member.getLockedTime().plusHours(1))) {
                throw UserException.USER_LOCKED;
            } else {
                // 被锁一个小时后解锁
                editMember.setIsLocked(false);
            }
        }
        // 判断密码
        if (!MD5.md5(password).equals(dMember.getPassword())) {
            // 密码错误，错误次数加一
            editMember.setErrorTimes(member.getErrorTimes() + 1);
            // 判断是否连续错误4次，是则锁账户
            if (editMember.getErrorTimes() >= 4) {
                editMember.setIsLocked(true);
                editMember.setErrorTimes(0);
                editMember.setLockedTime(LocalDateTime.now());
            }
            memberMapper.updateByPrimaryKeySelective(editMember);
            throw UserException.WRONG_USERNAME_OR_PASSWORD;
        }
        // 更新用户信息
        String token = getEncToken(member.getMemberId());
        editMember.setNickName(dMember.getNickName());
        editMember.setAreaCode(dMember.getAreaCode());
        editMember.setMobile(dMember.getMobile());
        editMember.setToken(token);
        editMember.setErrorTimes(0);
        memberMapper.updateByPrimaryKeySelective(editMember);

        // 统计用户订单信息
        UserOrderInfoCountDTO orderCount = userOrderMapper.orderCount(member.getMemberId());
        DMemberAccount memberAccount = dMemberAccountMapper.selectByPrimaryKey(member.getMemberId());

        // 返回用户信息
        Double incomeRate = 0.0;
        if (orderCount.getTotalFee() > 0) {
            incomeRate = BigDecimal.valueOf(orderCount.getClosedIncome())
                    .add(BigDecimal.valueOf(orderCount.getFloatProfitLoss()))
                    .divide(BigDecimal.valueOf(orderCount.getTotalFee()), 4 ,BigDecimal.ROUND_DOWN)
                    .multiply(BigDecimal.valueOf(100)).doubleValue();
        }
        MemberDetailDTO memberDetailDTO = MemberDetailDTO.builder()
                .memberId(member.getMemberId()).token(token)
                .nickName(member.getNickName()).depositRate(orderCount.getDepositRate())
                .floatProfitLoss(orderCount.getFloatProfitLoss())
                .accountEquity(memberAccount.getUsdBalance() / 100.0)
              /*  .accountEquity(BigDecimal.valueOf(orderCount.getTotalFee() +
                        orderCount.getFloatProfitLoss() + orderCount.getClosedIncome()).setScale(2, BigDecimal.ROUND_DOWN).doubleValue())*/
                .availableDeposit(orderCount.getAvailableDeposit()).usedDeposit(orderCount.getUsedDeposit())
                .incomeRate(incomeRate).closedIncome(orderCount.getClosedIncome())
                .build();
        // 帮用户自动注册mt5账号
        mt5HandleService.autoRegister(member.getMemberId());
        return memberDetailDTO;
    }

    /**
     * 用户详情
     * @param token
     * @return
     */
    public MemberDetailDTO detail(String token) {
        Member member = validateUserToken(token);

        // 统计用户订单信息
        UserOrderInfoCountDTO orderCount = userOrderMapper.orderCount(member.getMemberId());
        DMemberAccount memberAccount = dMemberAccountMapper.selectByPrimaryKey(member.getMemberId());

        // 返回用户信息
        Double incomeRate = 0.0;
        if (orderCount.getTotalFee() > 0) {
            incomeRate = BigDecimal.valueOf(orderCount.getClosedIncome())
                    .add(BigDecimal.valueOf(orderCount.getFloatProfitLoss()))
                    .divide(BigDecimal.valueOf(orderCount.getTotalFee()), 4 ,BigDecimal.ROUND_DOWN)
                    .multiply(BigDecimal.valueOf(100)).doubleValue();
        }
        MemberDetailDTO memberDetailDTO = MemberDetailDTO.builder()
                .memberId(member.getMemberId()).token(token)
                .nickName(member.getNickName()).depositRate(orderCount.getDepositRate())
                .floatProfitLoss(orderCount.getFloatProfitLoss())
                .accountEquity(memberAccount.getUsdBalance() / 100.0)
              /*  .accountEquity(BigDecimal.valueOf(orderCount.getTotalFee() +
                        orderCount.getFloatProfitLoss() + orderCount.getClosedIncome()).setScale(2, BigDecimal.ROUND_DOWN).doubleValue())*/
                .availableDeposit(memberAccount.getUsdBalance() / 100.0).usedDeposit(orderCount.getUsedDeposit())
                .incomeRate(incomeRate).closedIncome(orderCount.getClosedIncome())
                .build();
        return memberDetailDTO;
    }

}
