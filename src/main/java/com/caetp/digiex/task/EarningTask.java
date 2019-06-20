package com.caetp.digiex.task;

import com.caetp.digiex.service.EarningsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * 计算订单日收益与用户日收益
 *
 * Created by wangzy on 2019/3/25.
 */
@Component
public class EarningTask {

    @Autowired
    private EarningsService earningsService;

    @Scheduled(cron = "0 0 12 * * ?")
    public void calculateOrderEarnings() {
        earningsService.calculateOrderEarnings(LocalDateTime.now());
    }

    @Scheduled(cron = "0 0 13 * * ?")
    public void calculateUserEarnings() {
        earningsService.calculateUserEarnings(LocalDateTime.now());
    }
}
