package com.caetp.digiex.service;

import com.caetp.digiex.config.CustomTransaction;
import com.caetp.digiex.consts.ProjectConsts;
import com.caetp.digiex.dto.PageInfoDTO;
import com.caetp.digiex.dto.TPage;
import com.caetp.digiex.dto.cms.Mt5OrderCmsDto;
import com.caetp.digiex.dto.cms.Mt5OrderDetailCmsDto;
import com.caetp.digiex.dto.cms.UserOrderCmsDto;
import com.caetp.digiex.dto.cms.UserOrderMT5CmsDto;
import com.caetp.digiex.entity.*;
import com.caetp.digiex.entity.mapper.AiRobotMapper;
import com.caetp.digiex.entity.mapper.Mt5OrderMapper;
import com.caetp.digiex.entity.mapper.Mt5UserOrderMapper;
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
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class Mt5OrderService extends BaseService {

    @Autowired
    Mt5OrderMapper mt5OrderMapper;
    @Autowired
    private UserOrderMapper userOrderMapper;
    @Autowired
    private AiRobotMapper aiRobotMapper;
    @Autowired
    private Mt5UserOrderMapper mt5UserOrderMapper;
    @Autowired
    private DMemberAccountMapper dMemberAccountMapper;
    @Autowired
    private DMemberAccountDetailsMapper dMemberAccountDetailsMapper;


    /**
     * 未完成订单列表（一个ai机器人最多有一单且在没有持仓订单的情况下）
     */
    public TPage<Mt5OrderCmsDto> selectMt5UndoneList(Integer pageNumber, Integer pageSize, Long aiRobotId){
        // 判断当前ai机器人有无持仓订单，应先平仓后建仓
        int mt5Orders = mt5OrderMapper.tradingMt5Orders(aiRobotId);
        if (mt5Orders > 0) {
            throw UserException.HAS_UNDEALT_MT5_ORDERS;
        }
        // 查询当前ai机器人下面待建仓、持仓中、待平仓的用户订单，截止时间为当前日期8点前
        LocalDateTime nowDate = LocalDateTime.of(LocalDate.now(), LocalTime.parse("08:00:00"));
        Mt5OrderCmsDto mt5OrderCmsDto = mt5OrderMapper.selectUndoneMt5Order(aiRobotId, nowDate);
        List<Mt5OrderCmsDto> list = new ArrayList<>();
        if (mt5OrderCmsDto.getTotalStandardHands() > 0.0) {
            mt5OrderCmsDto.setOrderId(TradeNoUtil.getTradeNo());
            mt5OrderCmsDto.setCutoff(nowDate);
            list.add(mt5OrderCmsDto);
        }
        return TPage.of(list, list.size());
    }

    /**
     * 持仓订单列表
     */
    public TPage<Mt5OrderCmsDto> selectMt5PositionList(Integer pageNumber, Integer pageSize, Long aiRobotId){
        List<Mt5OrderCmsDto> list = PageHelper.startPage(pageNumber,pageSize).doSelectPage(() ->
                mt5OrderMapper.selectMt5PositionList(aiRobotId));
        PageInfo<Mt5OrderCmsDto> pageInfo = new PageInfo<>(list);
        PageInfoDTO pageInfoDTO = new PageInfoDTO((int) pageInfo.getTotal(), pageNumber, pageInfo.getPages(), pageSize);

        return TPage.of(list, pageInfo.getTotal(), pageInfoDTO);
    }

    /**
     * 平仓订单
     */
    public TPage<Mt5OrderCmsDto> selectMt5EveningUpList(Integer pageNumber, Integer pageSize, Long aiRobotId){
        List<Mt5OrderCmsDto> list = PageHelper.startPage(pageNumber,pageSize).doSelectPage(() ->
                mt5OrderMapper.selectMt5EveningUpList(aiRobotId));
        PageInfo<Mt5OrderCmsDto> pageInfo = new PageInfo<>(list);
        PageInfoDTO pageInfoDTO = new PageInfoDTO((int) pageInfo.getTotal(), pageNumber, pageInfo.getPages(), pageSize);

        return TPage.of(list, pageInfo.getTotal(), pageInfoDTO);
    }

    /**
     * 建仓
     */
    @CustomTransaction(value = {"digiexTransactionManager"})
    public  void updateMt5UndoneOrder(Long aiRobotId, String orderType, String aiType, String buyTimeStr,
                                      Double buyPrice, Double buyServiceFee){
        LocalDateTime buyTime = LocalDateTime.parse(buyTimeStr, DateTimeFormatter.ofPattern(ProjectConsts.DEFAULT_DATE_TIME_PATTERN));
        // 判断当前ai机器人有无持仓订单，应先平仓后建仓
        int mt5Orders = mt5OrderMapper.tradingMt5Orders(aiRobotId);
        if (mt5Orders > 0) {
            throw UserException.HAS_UNDEALT_MT5_ORDERS;
        }
        // 查询当前ai机器人下面待建仓、持仓中、待平仓的用户订单，截止时间为当前日期8点前
        LocalDateTime nowDate = LocalDateTime.of(LocalDate.now(), LocalTime.parse("08:00:00"));
        List<UserOrderCmsDto> list = mt5OrderMapper.selectMt5ByUndoneInfoView(aiRobotId, nowDate);
        // 统计建仓用户订单的总手数与总金额
        BigDecimal totalStandardHands = BigDecimal.valueOf(0);
        BigDecimal totalFee = BigDecimal.valueOf(0);
        for (UserOrderCmsDto orderCmsDto : list) {
            totalStandardHands = totalStandardHands.add(BigDecimal.valueOf(orderCmsDto.getStandardHands()));
            totalFee = totalFee.add(BigDecimal.valueOf(orderCmsDto.getFee()));
        }
        if (totalStandardHands.doubleValue() > 0) {
            AiRobot aiRobot = aiRobotMapper.selectByPrimaryKey(aiRobotId);
            if (aiRobot == null) {
                throw CommonException.INVALID_PARAMETER;
            }
            // 建仓，插入MT5订单
            String mt5OrderId = TradeNoUtil.getTradeNo();
            mt5OrderMapper.insertSelective(Mt5Order.builder()
                    .orderId(mt5OrderId).aiRobotId(aiRobotId)
                    .aiType(aiType.toUpperCase()).orderType(orderType)
                    .buyPrice(BigDecimal.valueOf(buyPrice))
                    .totalStandardHands(totalStandardHands).totalFee(totalFee)
                    .buyServiceFee(BigDecimal.valueOf(buyServiceFee))
                    .buyTime(buyTime).createdBy(1L)
                    .createdTime(LocalDateTime.now()).updatedTime(LocalDateTime.now())
                    .build());

            // 更新用户订单
            BigDecimal buyServiceFeePer = BigDecimal.valueOf(buyServiceFee).divide(totalStandardHands, 4, BigDecimal.ROUND_DOWN);
            for (UserOrderCmsDto orderCmsDto : list) {
                if (orderCmsDto.getStatus() == 0) {
                    // 未建仓的用户订单，建仓，更新订单信息，而持仓中、待平仓的用户订单则继续跟单
                    int count = userOrderMapper.userOrderToBuy(orderCmsDto.getId(),
                            buyServiceFeePer.multiply(BigDecimal.valueOf(orderCmsDto.getStandardHands())), buyTime);
                    if (count == 0) {
                        // 用户订单建仓失败，可能由于用户已撤销订单
                        throw UserException.HAS_USER_ORDER_CANCEL;
                    }
                } else {
                    // 更新建仓手续费
                    userOrderMapper.updateByPrimaryKeySelective(UserOrder.builder()
                            .id(orderCmsDto.getId())
                            .buyServiceFee(buyServiceFeePer.multiply(BigDecimal.valueOf(orderCmsDto.getStandardHands()))
                                    .add(orderCmsDto.getBuyServiceFee()))
                            .build());
                }
                // 用户订单与MT5订单建立跟单关联
                mt5UserOrderMapper.insertSelective(Mt5UserOrder.builder()
                        .mt5OrderId(mt5OrderId).userOrderId(orderCmsDto.getId())
                        .buyServiceFee(buyServiceFeePer.multiply(BigDecimal.valueOf(orderCmsDto.getStandardHands()))).build());
            }
        } else {
            throw CommonException.INVALID_PARAMETER;
        }
    }

    /**
     * 平仓
     */
    @CustomTransaction(value = {"digiexTransactionManager","digiexrmsTransactionManager"})
    public void updateMt5OrderAndPosition(String orderId, String sellTimeStr, Double sellPrice, Double sellServiceFee,
                                          Double inventoryFee, Double totalEarnings){
        // 更新MT5订单
        Mt5Order mt5Order = mt5OrderMapper.selectByPrimaryKey(orderId);
        if (mt5Order == null || mt5Order.getStatus() != 0) {
            throw UserException.NOT_FOUND_RESULT;
        }
        LocalDateTime sellTime = LocalDateTime.parse(sellTimeStr, DateTimeFormatter.ofPattern(ProjectConsts.DEFAULT_DATE_TIME_PATTERN));
        int count = mt5OrderMapper.sellMt5Order(orderId, BigDecimal.valueOf(sellPrice),
                BigDecimal.valueOf(sellServiceFee), BigDecimal.valueOf(totalEarnings), sellTime,BigDecimal.valueOf(inventoryFee));
        if (count == 0) {
            throw CommonException.UPDATE_FAIL;
        }

        // 更新MT5订单下的用户订单信息
        List<UserOrderMT5CmsDto> mt5UserOrderList = mt5OrderMapper.mt5UserOrderList(orderId);
        BigDecimal sellServiceFeePer = BigDecimal.valueOf(sellServiceFee).divide(mt5Order.getTotalStandardHands(), 4, BigDecimal.ROUND_DOWN);
        BigDecimal earningsPer = BigDecimal.valueOf(totalEarnings).divide(mt5Order.getTotalStandardHands(), 4, BigDecimal.ROUND_DOWN);
        BigDecimal inventoryFeePer = BigDecimal.valueOf(inventoryFee).divide(mt5Order.getTotalStandardHands(),4,BigDecimal.ROUND_DOWN);
        BigDecimal sellServiceFeeOrder, earningsOrder, inventoryFeeOrder, totalEarningsOrder;
        Boolean autoSell;
        UserOrder editUserOrder;
        for (UserOrderMT5CmsDto dto : mt5UserOrderList) {
            autoSell = false;
            sellServiceFeeOrder = sellServiceFeePer.multiply(dto.getStandardHands());
            earningsOrder = earningsPer.multiply(dto.getStandardHands());
            totalEarningsOrder = earningsOrder.add(dto.getEarnings());
            inventoryFeeOrder = inventoryFeePer.multiply(dto.getStandardHands());
            // 判断总收益（亏损）是否已超出保证金止损限制条件，超出时则自动平仓止损
            BigDecimal lossLimit = dto.getPettyCash().multiply(
                    BigDecimal.valueOf(1).subtract(dto.getStopLossLimit()))
                    .negate();
            if (totalEarningsOrder.compareTo(lossLimit) < 0) {
                // 总亏损已超保证金止损限制条件，自动平仓
                autoSell = true;
                if (dto.getStatus() == 1) {
                    dto.setStatus(2);
                }
            }
            if (dto.getStatus() == 2) {
                // 待平仓的用户订单，进行平仓，并将本金 + 备用金 + 总收益返回给用户USD账户
                editUserOrder = UserOrder.builder()
                        .id(dto.getId()).sellServiceFee(sellServiceFeeOrder.add(dto.getSellServiceFee()))
                        .status(3).earnings(totalEarningsOrder)
                        .inventoryFee(inventoryFeeOrder.add(dto.getInventoryFee())).sellTime(sellTime)
                        .build();
                if (autoSell) {
                    editUserOrder.setSellType("auto");
                }
                count = userOrderMapper.updateByPrimaryKeySelective(editUserOrder);
                if (count == 0) {
                    throw CommonException.UPDATE_FAIL;
                }
                // 将本金 + 备用金 + 总收益返回给用户USD账户
                count = dMemberAccountMapper.updateAccountByPrimaryKeySelective(DMemberAccountIncrement.builder()
                        .memberId(dto.getMemberId())
                        .usdBalanceIncrement(dto.getFee().add(totalEarningsOrder)
                                .add(dto.getPettyCash()).multiply(BigDecimal.valueOf(100)).longValue()).build());
                // 插入USD账户的流水明细
                DMemberAccountDetails accountDetails = DMemberAccountDetails.builder()
                        .memberId(dto.getMemberId()).linkId(dto.getId().toString())
                        .accountType("USD").amount(dto.getFee().add(totalEarningsOrder).add(dto.getPettyCash()))
                        .detailsType("ex_ai").createdTime(LocalDateTime.now())
                        .build();
                dMemberAccountDetailsMapper.insertSelective(accountDetails);
                if (count == 0) {
                    throw CommonException.UPDATE_FAIL;
                }
            } else {
                // 待平仓的用户订单，更新收益
                count = userOrderMapper.updateByPrimaryKeySelective(UserOrder.builder()
                        .id(dto.getId()).inventoryFee(inventoryFeeOrder.add(dto.getInventoryFee()))
                        .sellServiceFee(sellServiceFeeOrder.add(dto.getSellServiceFee()))
                        .earnings(totalEarningsOrder).build());
                if (count == 0) {
                    throw CommonException.UPDATE_FAIL;
                }
            }
            // 更新MT5_User_Order信息
            mt5UserOrderMapper.updateByPrimaryKeySelective(Mt5UserOrder.builder()
                    .mt5OrderId(orderId).userOrderId(dto.getId())
                    .inventoryFee(inventoryFeeOrder).sellServiceFee(sellServiceFeeOrder)
                    .earnings(earningsOrder).sellTime(sellTime).build());
        }

        // 更新ai机器人统计数据
        AiRobot aiRobot = aiRobotMapper.selectByPrimaryKey(mt5Order.getAiRobotId());
        AiRobot editAiRobot = new AiRobot();
        editAiRobot.setId(aiRobot.getId());
        editAiRobot.setTotalTransactions(aiRobot.getTotalTransactions() + 1);
        editAiRobot.setSellEarnings(aiRobot.getSellEarnings().add(BigDecimal.valueOf(totalEarnings)));
        if (totalEarnings >= 0) {
            if (totalEarnings > aiRobot.getMaxProfit().doubleValue()) {
                editAiRobot.setMaxProfit(BigDecimal.valueOf(totalEarnings));
            }
            editAiRobot.setProfitOrders(aiRobot.getProfitOrders() + 1);
        } else {
            if (totalEarnings < aiRobot.getMaxLoss().doubleValue()) {
                editAiRobot.setMaxLoss(BigDecimal.valueOf(totalEarnings));
                editAiRobot.setLossOrders(aiRobot.getLossOrders() + 1);
            }
            editAiRobot.setProfitOrders(aiRobot.getProfitOrders());
        }
        if (mt5Order.getTotalStandardHands().doubleValue() > aiRobot.getMaxStandardHands().doubleValue()) {
            editAiRobot.setMaxStandardHands(mt5Order.getTotalStandardHands());
        }
        editAiRobot.setAccuracyRate(BigDecimal.valueOf(editAiRobot.getProfitOrders())
                .divide(BigDecimal.valueOf(editAiRobot.getTotalTransactions()), 4, BigDecimal.ROUND_DOWN)
                .multiply(BigDecimal.valueOf(100)));
        editAiRobot.setAverageProfit(editAiRobot.getSellEarnings()
                .divide(BigDecimal.valueOf(editAiRobot.getTotalTransactions()), 4, BigDecimal.ROUND_DOWN));
        // TODO 月盈利率，单位%尚未统计计算，先使用数据库初始化数据
        aiRobotMapper.updateByPrimaryKeySelective(editAiRobot);
    }

    /**
     * MT5持仓或者平仓订单详情
     * @param orderId
     * @return
     */
    public Mt5OrderDetailCmsDto mt5OrderDetail(String orderId) {
        Mt5OrderDetailCmsDto mt5OrderDetailCmsDto = mt5OrderMapper.mt5OrderDetail(orderId);
        if(ProjectConsts.DB_BUY.equals(mt5OrderDetailCmsDto.getOrderType())){
                mt5OrderDetailCmsDto.setOrderType(ProjectConsts.BUY);
        }else{
            mt5OrderDetailCmsDto.setOrderType(ProjectConsts.SELL);
        }
        return mt5OrderDetailCmsDto;
    }

    /**
     * 未建仓MT5订单的用户订单列表
     */
    public TPage<UserOrderCmsDto> selectMt5ByUndoneInfoView(Integer pageNumber, Integer pageSize, Long aiRobotId){
        // 查询当前ai机器人下面待建仓、持仓中、待平仓的用户订单，截止时间为当前日期前
        LocalDateTime nowDate = LocalDateTime.of(LocalDate.now(), LocalTime.parse("08:00:00"));
        List<UserOrderCmsDto> list = PageHelper.startPage(pageNumber,pageSize).doSelectPage(() ->
                mt5OrderMapper.selectMt5ByUndoneInfoView(aiRobotId, nowDate));
        PageInfo<UserOrderCmsDto> pageInfo = new PageInfo<>(list);
        PageInfoDTO pageInfoDTO = new PageInfoDTO((int) pageInfo.getTotal(), pageNumber, pageInfo.getPages(), pageSize);

        return TPage.of(list, pageInfo.getTotal(), pageInfoDTO);
    }

    /**
     * 持仓或平仓MT5订单的用户订单列表
     */
    public TPage<UserOrderCmsDto> userOrdersByMT5OrderId(Integer pageNumber, Integer pageSize, String orderId) {
        List<UserOrderCmsDto> list = PageHelper.startPage(pageNumber,pageSize).doSelectPage(() ->
                mt5OrderMapper.userOrdersByMT5OrderId(orderId));
        PageInfo<UserOrderCmsDto> pageInfo = new PageInfo<>(list);
        PageInfoDTO pageInfoDTO = new PageInfoDTO((int) pageInfo.getTotal(), pageNumber, pageInfo.getPages(), pageSize);

        return TPage.of(list, pageInfo.getTotal(), pageInfoDTO);
    }
}
