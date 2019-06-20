package com.caetp.digiex.service;

import com.caetp.digiex.config.CustomTransaction;
import com.caetp.digiex.dto.PageInfoDTO;
import com.caetp.digiex.dto.TPage;
import com.caetp.digiex.dto.api.AiRobotDetailDTO;
import com.caetp.digiex.dto.api.AiRobotListDTO;
import com.caetp.digiex.dto.cms.AIRobotCmsNameDTO;
import com.caetp.digiex.dto.cms.AiRobotCmsDto;
import com.caetp.digiex.entity.*;
import com.caetp.digiex.entity.mapper.AiRobotMapper;
import com.caetp.digiex.entity.mapper.UserOrderMapper;
import com.caetp.digiex.entity.rmsmapper.DMemberAccountDetailsMapper;
import com.caetp.digiex.entity.rmsmapper.DMemberAccountMapper;
import com.caetp.digiex.exception.CommonException;
import com.caetp.digiex.exception.UserException;
import com.caetp.digiex.utli.common.TradeNoUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by hy on 2018/2/17.
 */
@Service
public class AiRobotService extends BaseService {

    @Autowired
    private AiRobotMapper aiRobotMapper;
    @Autowired
    private DMemberAccountMapper dMemberAccountMapper;
    @Autowired
    private UserOrderMapper userOrderMapper;
    @Autowired
    private DMemberAccountDetailsMapper dMemberAccountDetailsMapper;

    @Autowired
    private MemberService memberService;

    /**
     * AI列表
     * @param token
     * @return
     */
    public TPage<AiRobotListDTO> aiRobotList(String token){
        validateUserToken(token);

        List<AiRobotListDTO> aiRobotList = aiRobotMapper.aiRobotList();
        return TPage.of(aiRobotList, aiRobotList.size());
    }

    /**
     * AI详情
     * @param token
     * @param id
     * @return
     */
    public AiRobotDetailDTO aiRobotDetail(String token, Long id){
        validateUserToken(token);

        return aiRobotMapper.aiRobotDetail(id);
    }


    /**
     * 跟随AI交易
     * @param token
     * @param id
     * @param totalStandardHands
     * @param location
     */
    @CustomTransaction(value = {"digiexTransactionManager","digiexrmsTransactionManager"})
    public void transactionAi(String token, Long id, Double totalStandardHands, String location) {
        Member member = validateUserToken(token);

        // 购买手数不能小于等于0
        if (totalStandardHands <= 0) {
            throw CommonException.INVALID_PARAMETER;
        }

        DMemberAccount dMemberAccount = dMemberAccountMapper.selectByPrimaryKey(member.getMemberId());
        Double usdBalance = dMemberAccount.getUsdBalance() / 100.0;       //用户美元余额
        // 首次跟单是判断美元余额是否小于1000
        Long orderCount = userOrderMapper.orderCountByMemberId(member.getMemberId());
        if (orderCount == 0) {
            if (usdBalance < 1000) {
               throw UserException.FIRST_ORDER_LIMIT;
            }
        }
        // 获取AI信息
        AiRobot aiRobot = aiRobotMapper.selectByPrimaryKey(id);
        if (aiRobot == null) {
            throw CommonException.INVALID_PARAMETER;
        }
        // 杠杆倍数
        BigDecimal leverageTimes = BigDecimal.valueOf(aiRobot.getLeverageTimes());
        // 保证金占比
        BigDecimal depositRate = BigDecimal.valueOf(aiRobot.getDepositRate());
        // 本金占比
        BigDecimal feeRate = BigDecimal.valueOf(aiRobot.getFeeRate());
        // 交易本金
        BigDecimal fee = BigDecimal.valueOf(totalStandardHands * 100000).divide(leverageTimes, 2, BigDecimal.ROUND_UP);
        // 保证金
        BigDecimal pettyCash = fee.multiply(depositRate).divide(feeRate, 2, BigDecimal.ROUND_UP);
        BigDecimal totalFee = fee.add(pettyCash);
        if(usdBalance < totalFee.doubleValue()) {
            // 判断USD余额
            throw UserException.INSUFFICIENT_MARGIN;
        }
        // 用户下单
        UserOrder userOrder = UserOrder.builder()
                .orderId(TradeNoUtil.getTradeNo()).type("ai").aiRobotId(id)
                .memberId(member.getMemberId()).standardHands(BigDecimal.valueOf(totalStandardHands))
                .fee(fee).pettyCash(pettyCash).status(0).isDeleted(false)
                .createdTime(LocalDateTime.now()).location(location)
                .build();
        userOrderMapper.insertSelective(userOrder);

       /* System.out.println(1 / 0);*/

        // 插入USD账户的流水明细
        DMemberAccountDetails accountDetails = DMemberAccountDetails.builder()
                .memberId(member.getMemberId()).linkId(userOrder.getId().toString())
                .accountType("USD").amount(totalFee.negate())
                .detailsType("ex_ai").createdTime(LocalDateTime.now())
                .build();
        dMemberAccountDetailsMapper.insertSelective(accountDetails);

        //扣除订单费用
        DMemberAccountIncrement account = DMemberAccountIncrement.builder()
                .memberId(member.getMemberId())
                .usdBalanceIncrement(-totalFee.multiply(BigDecimal.valueOf(100)).longValue())
                .updatedBy(member.getMemberId()).updatedTime(LocalDateTime.now())
                .build();
        dMemberAccountMapper.updateAccountByPrimaryKeySelective(account);
    }


    /**
     * 查询ai管理列表数据
     *
     * @return
     */
    public TPage<AiRobot> aiRobotInfo(Integer pageNumber, Integer pageSize) {
        List<AiRobot> list = PageHelper.startPage(pageNumber,pageSize).doSelectPage(() ->
                aiRobotMapper.selectByList());
        PageInfo<AiRobot> pageInfo = new PageInfo<>(list);
        PageInfoDTO pageInfoDTO = new PageInfoDTO((int) pageInfo.getTotal(), pageNumber, pageInfo.getPages(), pageSize);
        return TPage.of(list, pageInfo.getTotal(), pageInfoDTO);
    }


    /**
     * 新增ai机器人
     * @param aiRobotCmsDto
     * @return
     */
    public Integer addAiRobot(AiRobotCmsDto aiRobotCmsDto) {
        // 杠杆倍数，保证金、本金占比、止损百分数都要大于0
        if (aiRobotCmsDto.getLeverageTimes() <= 0 || aiRobotCmsDto.getDepositRate() <= 0
                || aiRobotCmsDto.getFeeRate() <= 0 || aiRobotCmsDto.getStopLossLimit() <= 0) {
            throw CommonException.INVALID_PARAMETER;
        }

        if (aiRobotCmsDto.getFeatures() == null) {
            aiRobotCmsDto.setFeatures("");
        }
        if (aiRobotCmsDto.getFeatures().split(",").length > 3) {
            // 标签不能超过3个
            throw CommonException.INVALID_PARAMETER;
        }

        AiRobot aiRobot = AiRobot.builder()
                .name(aiRobotCmsDto.getName()).aiType(aiRobotCmsDto.getAiType())
                .aiTypeName(aiRobotCmsDto.getAiTypeName()).agreementName(aiRobotCmsDto.getAgreementName())
                .agreementUrl(aiRobotCmsDto.getAgreementUrl()).isActivated(aiRobotCmsDto.getIsActivated())
                .isDeleted(false).createdBy(1L).createdTime(LocalDateTime.now()).updatedBy(1L)
                .updatedTime(LocalDateTime.now()).leverageTimes(aiRobotCmsDto.getLeverageTimes())
                .depositRate(aiRobotCmsDto.getDepositRate()).feeRate(aiRobotCmsDto.getFeeRate())
                .features(aiRobotCmsDto.getFeatures()).stopLossLimit(BigDecimal.valueOf(aiRobotCmsDto.getStopLossLimit())
                        .divide(BigDecimal.valueOf(100), 2, BigDecimal.ROUND_DOWN))
                .build();
        return aiRobotMapper.insertSelective(aiRobot);
    }

    /**
     * 删除Ai机器人
     * @param id
     * @return
     */
    public Integer delAiRobot(Long id) {
        int robotCount = aiRobotMapper.aiRobtCount(id);
        if(robotCount>0){
            throw UserException.HAS_USER_ORDER;
        }
        AiRobot aiRobot = AiRobot.builder()
                .id(id).isDeleted(true)
                .build();
        return aiRobotMapper.updateByPrimaryKeySelective(aiRobot);
    }


    /**
     * 查询AI机器人详情
     * @param id
     * @return
     */
    public AiRobotCmsDto aiRobotView(Long id) {
        return aiRobotMapper.aiRobotView(id);
    }

    /**
     * 编辑ai机器人
     * @param aiRobotCmsDto
     * @return
     */
    public Integer modifyRobotView(AiRobotCmsDto aiRobotCmsDto) {
        // 杠杆倍数，保证金、本金占比、止损百分数都要大于0
        if (aiRobotCmsDto.getLeverageTimes() <= 0 || aiRobotCmsDto.getDepositRate() <= 0
                || aiRobotCmsDto.getFeeRate() <= 0 || aiRobotCmsDto.getStopLossLimit() <= 0) {
            throw CommonException.INVALID_PARAMETER;
        }

        if (aiRobotCmsDto.getFeatures() == null) {
            aiRobotCmsDto.setFeatures("");
        }
        if (aiRobotCmsDto.getFeatures().split(",").length > 3) {
            // 标签不能超过3个
            throw CommonException.INVALID_PARAMETER;
        }
        AiRobot aiRobot = AiRobot.builder()
                .id(aiRobotCmsDto.getId()).name(aiRobotCmsDto.getName()).aiType(aiRobotCmsDto.getAiType())
                .aiTypeName(aiRobotCmsDto.getAiTypeName()).agreementName(aiRobotCmsDto.getAgreementName())
                .agreementUrl(aiRobotCmsDto.getAgreementUrl()).isActivated(aiRobotCmsDto.getIsActivated())
                .updatedBy(1L).updatedTime(LocalDateTime.now()).leverageTimes(aiRobotCmsDto.getLeverageTimes())
                .depositRate(aiRobotCmsDto.getDepositRate()).feeRate(aiRobotCmsDto.getFeeRate())
                .features(aiRobotCmsDto.getFeatures()).stopLossLimit(BigDecimal.valueOf(aiRobotCmsDto.getStopLossLimit())
                        .divide(BigDecimal.valueOf(100), 2, BigDecimal.ROUND_DOWN))
                .build();
        return aiRobotMapper.updateByPrimaryKeySelective(aiRobot);
    }


    public List<AIRobotCmsNameDTO> aiRobotByName() {
        return aiRobotMapper.aiRobotByName();
    }
}
