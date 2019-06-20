package com.caetp.digiex.utli.common;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import okhttp3.Request;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by wangzy on 2018/11/13.
 */
public class BankCardUtil {

    //银行卡解析
    public static final String APP_CODE = "5a5fd24e1c9f4d9c9da22aa59496a246";
    public static final String BANK_AUTH_URL = "http://bankcardin.market.alicloudapi.com/efficient/bankcard/getcardinfo?cardNo={0}";
    public static final String AUTHORIZATION = "Authorization";
    public static final String AUTHORIZATION_HEADER = "APPCODE {0}";

    /**
     * 银行卡解析
     * @param account
     * @return
     * @throws Exception
     */
    public static Map<String, String> bankAuth(String account) {
        String url = MessageFormat.format(BANK_AUTH_URL, account);
        Request request = new Request.Builder()
                .url(url)
                .header(AUTHORIZATION, MessageFormat.format(AUTHORIZATION_HEADER, APP_CODE))
                .build();
        String resultStr = null;
        try {
            resultStr = OkHttpUtil.executeString(request);
        } catch (IOException e) {
            e.printStackTrace();
        }
        JSONObject jsonObject = JSON.parseObject(resultStr);
        JSONObject resultData = jsonObject.getJSONObject("result");
        Map<String, String> result = new HashMap<>();
        if (resultData != null) {
            result.put("bankName", resultData.getString("BankName")); // 工商银行
            result.put("cardType", resultData.getString("CardType")); // 借记卡
        }
        return result;
    }

    /**
     * 校验银行卡卡号
     * @param cardId
     * @return
     */
    public static boolean checkBankCard(String cardId) {
        char bit = getBankCardCheckCode(cardId.substring(0, cardId.length() - 1));
        if(bit == 'N'){
            return false;
        }
        return cardId.charAt(cardId.length() - 1) == bit;
    }

    /**
     * 从不含校验位的银行卡卡号采用 Luhm 校验算法获得校验位
     * @param nonCheckCodeCardId
     * @return
     */
    public static char getBankCardCheckCode(String nonCheckCodeCardId){
        if(nonCheckCodeCardId == null || nonCheckCodeCardId.trim().length() == 0
                || !nonCheckCodeCardId.matches("\\d+")) {
            //如果传的不是数据返回N
            return 'N';
        }
        char[] chs = nonCheckCodeCardId.trim().toCharArray();
        int luhmSum = 0;
        for(int i = chs.length - 1, j = 0; i >= 0; i--, j++) {
            int k = chs[i] - '0';
            if(j % 2 == 0) {
                k *= 2;
                k = k / 10 + k % 10;
            }
            luhmSum += k;
        }
        return (luhmSum % 10 == 0) ? '0' : (char)((10 - luhmSum % 10) + '0');
    }

    public static void main(String[] args) {
        System.out.println(checkBankCard("6214910313771260"));
//        System.out.println(checkBankCard("6222021903004380197"));
//        System.out.println(bankAuth("6222021903004380196")); // 有效卡
        System.out.println(bankAuth("6214910313771260"));
//        System.out.println(bankAuth("622202190300438019").isEmpty());// 无效卡
    }

}
