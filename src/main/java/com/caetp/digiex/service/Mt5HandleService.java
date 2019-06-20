package com.caetp.digiex.service;

import com.alibaba.fastjson.JSON;
import com.caetp.digiex.consts.ProjectConsts;
import com.caetp.digiex.dto.api.DmemberAccount;
import com.caetp.digiex.entity.*;
import com.caetp.digiex.entity.mapper.MemberMt5Mapper;
import com.caetp.digiex.entity.mapper.UserMt5OrderMapper;
import com.caetp.digiex.entity.rmsmapper.DMemberAccountMapper;
import com.caetp.digiex.entity.rmsmapper.DMemberMapper;
import com.caetp.digiex.utli.common.GetMemberId;
import org.java_websocket.client.WebSocketClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**发送到mt5之前的处理以及失败处理服务
 * Created by gaoyx on 2019/5/16.
 */

@Service
public class Mt5HandleService extends BaseService{

    @Autowired
    RegisterService registerService;
    @Autowired
    DMemberAccountMapper dMemberAccountMapper;
    @Autowired
    MemberMt5Mapper memberMt5Mapper;
    @Autowired
    UserMt5OrderMapper userMt5OrderMapper;
    @Autowired
    WebSocketClient webSocketClient;
    @Autowired
    DMemberMapper dMemberMapper;




    /** 开仓成功更新订单id
     * @param reqParamerMap
     */
    private void updateOrder(Map reqParamerMap) {
        UserMt5Order userMt5Order = UserMt5Order.builder().order((Long) reqParamerMap.get("orderid"))
                .memberId(Integer.parseInt(GetMemberId.getMebmerId(reqParamerMap))).build();
        userMt5OrderMapper.updateByPrimaryKeySelective(userMt5Order);
    }

    /** 更新数据库中mt5账户的equity
     * @param reqParamerMap
     */
    private void updateMemberMt5(Map reqParamerMap) {
        String amountStr = reqParamerMap.get("amount").toString();
        MemberMt5 memberMt5 = MemberMt5.builder().equity(Double.valueOf(amountStr))
                .memberId(Integer.parseInt(GetMemberId.getMebmerId(reqParamerMap))).build();
       /* memberMt5Mapper.updateEquityByPrimaryKey(memberMt5);*/
    }

    /**
     *  给请求参数中的reqid添加谓词,并在发送前做一些处理
     *
     */
    public String updateReqid(String reqParameter) {
        Map reqParameterMap = (Map) JSON.parseObject(reqParameter);
        String reqid = (String) reqParameterMap.get("reqid");

        // 划转前判断
        String reqOperation = getOperation(reqParameterMap);
        if ("ioMoney".equals(reqOperation)) {
            DMemberAccount userUsdAmount = dMemberAccountMapper.getUserUsdAmount(GetMemberId
                    .getMebmerId(reqParameterMap));
            Long usdBalanceLong = userUsdAmount.getUsdBalance();
            BigDecimal usdBalanceBig = new BigDecimal(usdBalanceLong);
            Double usdBalanceDouble = usdBalanceBig.divide(new BigDecimal("100")).doubleValue();
            Double amount = null;
            Object amountObj = reqParameterMap.get("amount");
            if (amountObj instanceof BigDecimal) { // BigDecimal
                amount = (((BigDecimal) amountObj).doubleValue());
            } else { // int
                String amountStr = ((Integer) amountObj).toString();
                amount = Double.parseDouble(amountStr + ".00");
            }
            if (amount > 0) { // 入金
                // 钱包账户余额不足
                if (amount > usdBalanceDouble) {
                    try {
                        WebSocketToAppService.webSocketToApp2S.get(reqid).sendMessage("钱包账户余额不足");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    return null;
                }
            }
        }
        reqid = reqid + "." + reqOperation;
        reqParameterMap.put("reqid", reqid);
        return JSON.toJSONString(reqParameterMap);
    }

    /** 根据请求参数确定本次是什么操作
     * @param reqParameterMap
     * @return
     */
    private String getOperation(Map reqParameterMap) {
        String reqtype = (String) reqParameterMap.get("reqtype");
        String operation = "";

        switch (reqtype) {
            case "register":   // 注册接口
                operation  = "register";
                break;
            case "deposituserinfo":  // 出入金/信用接口
                String operationtype = ((Integer) reqParameterMap.get("operationtype")).toString();
                switch (operationtype) {
                    case "2":
                        operation = "ioMoney";// 2 表示出入金（结余），
                        break;
                    case "3":
                        operation = "credit";// 3 表示信用，
                        break;
                    case "4":
                        operation = "charge";//4 费用
                        break;
                    case "5":
                        operation = "change";//5 更正
                        break;
                    case "6":
                        operation = "bonus";//6 红利
                        break;
                    case "7":
                        operation = "storage"; //7 手续费
                        break;
                }
                break;
            case "tradeorderinfo": // 市场开仓/市场挂单接口
                String order = ((Integer) reqParameterMap.get("order")).toString();
                String tradeType = ((Integer) reqParameterMap.get("tradetype")).toString();
                if ("0".equals(order)) {
                    if ("1".equals(tradeType)) {
                        operation = "openOrder";
                    } else if ("2".equals(tradeType)) {
                        operation = "suspendOrder";
                    } else if ("3".equals(tradeType)) {
                        operation = "updateOrder";
                    } else {
                        operation = "cancleOrder";
                    }
                } else {
                    operation = "closeOrder";
                }
                break;
            case "updateuserinfo":  // 更改用户信息
                operation = "updateuserinfo";
                break;
            case "ChangeAccountLevelAge":  // 修改杠杆
                operation = "ChangeAccountLevelAge";
                break;
            case "getuserinfo":  // 获取mt5账户信息
                operation = "getuserinfo";
                break;
            case "marginleveluserinfo":  // 查询账户资金
                operation = "marginleveluserinfo";
                break;
            case "ordersuserinfo":  // 用户持仓
                operation = "ordersuserinfo";
                break;
            case "historyorderinfo":  //历史订单查询
                operation = "historyorderinfo";
                break;
            case "getGroupPositionOrdersInfo": // 获取组信息
                operation = "getGroupPositionOrdersInfo";
                break;
        }
        return operation;
    }


    /** 确定订单类型
     * @param reqParameter
     * @return
     */
    public static String getOrderType(Map reqParameter) {
        Integer tradetype = (Integer) reqParameter.get("tradetype");
        Integer tradecmd = (Integer) reqParameter.get("tradecmd");
        String tradetype1 = tradetype.toString();
        String tradecmd2 = tradecmd.toString();
        String type = "";
        if ("1".equals(tradetype1)) {  // 市价
            if ("0".equals(tradecmd2)) { // 买入
                type =  ProjectConsts.DB_BUY1;
            } else {  // 卖出
                type = ProjectConsts.DB_SELL1;
            }
        } else {  // 挂单
            switch (tradecmd2) {
                case "2":  // OP_BUY_LIMIT=2,
                    type = ProjectConsts.DB_BUY_LIMIT;
                    break;
                case "3":  // OP_SELL_LIMIT=3
                    type = ProjectConsts.DB_SELL_LIMIT;
                    break;
                case "4":  // OP_BUY_STOP=4
                    type = ProjectConsts.DB_BUY_STOP;
                    break;
                case "5":  // OP_SELL_STOP=5
                    type = ProjectConsts.DB_SELL_STOP;
                    break;
                case "6":  // OP_BUY_STOP_LIMIT =6,
                    type = ProjectConsts.DB_BUY_STOP_LIMIT;
                    break;
                case "7":  // OP_SELL_STOP_LIMIT=7
                    type = ProjectConsts.DB_SELL_STOP_LIMIT;
                    break;
            }
        }
        return type;
    }

    /** 发送请求到mt5失败的处理
     * @param reqParameterMap
     * @param mt5ResultMap
     */
    public void fall(Map reqParameterMap, Map mt5ResultMap) {
        // 如果请求设计到加/扣款，将钱退回给用户
        //  出入金/信用接口
        if ("deposituserinfo".equals(reqParameterMap.get("reqtype"))) {
            if ("2".equals(reqParameterMap.get("operationtype"))) {
                // 出入金请求失败
                depositUserInfoFall(reqParameterMap);
            }
        }
    }

    /** 出入金请求失败后的处理
     * @param reqParameterMap
     */
    private void depositUserInfoFall(Map reqParameterMap) {
        // 将出入金的金额返回给用户
        Long amount = (Long) reqParameterMap.get("amount");
        DMemberAccountIncrement dMemberAccountIncrement = new DMemberAccountIncrement();
        dMemberAccountIncrement.setUsdBalanceIncrement(amount);
        dMemberAccountIncrement.setUpdatedTime(LocalDateTime.now());
        dMemberAccountMapper.updateAccountByPrimaryKeySelective(dMemberAccountIncrement);
    }


    /** 具体业务逻辑
     * @param reqParameter
     */
    public void sent2Mt5Service(String reqParameter) {
        Map reqParameterMap = JSON.parseObject(reqParameter);
        String operation = getOperation(reqParameterMap);
        switch (operation) {
            case "register": //注册
                webSocketClient.send(reqParameter);
                break;
            case "openOrder": //开仓
                // 先根据设计好的比例发送入金请求，成功以后再下开仓单
                sendDepositUserInfo(reqParameterMap);
                break;
            case "updateuserinfo":  // 更改mt5用户信息
                sendUpdateUserInfo(reqParameter);
                break;
            case "ChangeAccountLevelAge":
                sendChangeAccountLevelAge(reqParameter);
                break;
        }

    }

    /** 更改杠杆
     * @param reqParameter
     */
    private void sendChangeAccountLevelAge(String reqParameter) {
        webSocketClient.send(reqParameter);
    }

    /** 更改用户信息
     * @param reqParameter
     */
    private void sendUpdateUserInfo(String reqParameter) {
        webSocketClient.send(reqParameter);
    }

    /** 根据开仓和保证金的比例，进行入金操作
     * @param reqParameterMap
     */
    private void sendDepositUserInfo(Map reqParameterMap) {

        // 获取到本次下单的金额
        // reqParameterMap.get();
    }


    /** 用户登录后自动注册mt5账号
     * @param memberId
     */
    public  void autoRegister(Integer memberId) {
        MemberMt5 memberMt5 = memberMt5Mapper.selectByPrimaryKey(memberId);
        if (memberMt5 == null) {
            //  没有mt5账号，自动注册
            Map<String,Object> map = new HashMap<>();

            String reqtype = "register";
            String reqid = memberId + "." + System.currentTimeMillis();
            String mobile = dMemberMapper.selectByPrimaryKey(memberId).getMobile();
            Long login = 0L;
            Integer leverage = 1;
            String groupname = "real\\DIGUSDH-B";
            String password = "DIGUSDH" + memberId;
            String investor = memberId + "DIGUSDH";
            Integer agentaccount = 0;
            String phonepwd = password + "wd";
            map.put("reqtype", reqtype);
            map.put("reqid", reqid);
            map.put("username", mobile);
            map.put("login", login);
            map.put("leverage", leverage);
            map.put("groupname", groupname);
            map.put("password", password);
            map.put("investor", investor);
            map.put("agentaccount", agentaccount);
            map.put("phonepwd", phonepwd);
            String reqParameter = JSON.toJSONString(map);
            reqParameter = this.updateReqid(reqParameter);
            WebSocketToAppService.reqParamters.put(reqid, reqParameter);

            webSocketClient.send(reqParameter);
        }
    }
}
