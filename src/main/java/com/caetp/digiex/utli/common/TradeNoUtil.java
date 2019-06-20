package com.caetp.digiex.utli.common;


import java.util.Calendar;

/**
 * Created by wangzy on 2018/11/13.
 */
public class TradeNoUtil {

    /**
     * 生成订单号
     * @return
     */
    public static String getTradeNo() {
        StringBuffer tradeNo = new StringBuffer();
        Calendar now = Calendar.getInstance();
        String year = String.valueOf(now.get(Calendar.YEAR));
        String month = String.valueOf(now.get(Calendar.MONTH) + 1);
        String day = String.valueOf(now.get(Calendar.DAY_OF_MONTH));
        String hours = String.valueOf(now.get(Calendar.HOUR_OF_DAY));
        String minutes = String.valueOf(now.get(Calendar.MINUTE));
        String seconds = String.valueOf(now.get(Calendar.SECOND));
        String millisecond = String.valueOf(now.get(Calendar.MILLISECOND));
        month = month.length() == 1 ? "0" + month : month;
        day = day.length() == 1 ? "0" + day : day;
        hours = hours.length() == 1 ? "0" + hours : hours;
        minutes = minutes.length() == 1 ? "0" + minutes : minutes;
        seconds = seconds.length() == 1 ? "0" + seconds : seconds;
        millisecond = millisecond.length() == 1 ? "0" + millisecond : millisecond;
        millisecond = millisecond.length() == 2 ? "0" + millisecond : millisecond;
        String code = Utility.getRandomCode(3);
        tradeNo.append(year).append(month).append(day)
                .append(hours).append(minutes)
                .append(seconds).append(millisecond).append(code);
        return tradeNo.toString();
    }


    public static void main(String[] args) {
        System.out.println(getTradeNo());
    }
}
