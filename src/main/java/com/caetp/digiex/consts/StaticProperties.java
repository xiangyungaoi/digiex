package com.caetp.digiex.consts;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Created by wangzy on 2018/7/27.
 */
@Component
public class StaticProperties {

    public static String QI_NIU_ACCESS_KEY;

    public static String QI_NIU_SECRET_KEY;

    public static String QI_NIU_BUCKET;

    public static String QI_NIU_DOMAIN_URL;

    public static String ACCESS_KEY;
    public static String ACCESS_SECRET;
    public static String SIGN_TEXT;

    public static String TEMPLATE_CODE_VERIFICATION;
    public static String INTERNATIONAL_TEMPLATE_CODE_VERIFICATION;
    public static String MESSAGE_TEXT_VERIFICATION;

    public static String MARKET_DETAIL_URL;


    // 设置注入
    @Value("${qiniu.access_key}")
    public void setQiNiuAccessKey(String qiNiuAccessKey) {
        QI_NIU_ACCESS_KEY = qiNiuAccessKey;
    }

    @Value("${qiniu.secret_key}")
    public void setQiNiuSecretKey(String qiNiuSecretKey) {
        QI_NIU_SECRET_KEY = qiNiuSecretKey;
    }

    @Value("${qiuniu.bucket}")
    public void setQiNiuBucket(String qiNiuBucket) {
        QI_NIU_BUCKET = qiNiuBucket;
    }

    @Value("${qiniu.domain_url}")
    public void setQiNiuDomainUrl(String qiNiuDomainUrl) {
        QI_NIU_DOMAIN_URL = qiNiuDomainUrl;
    }

    @Value("${aliyun.sms.access.key}")
    public void setAccessKey(String accessKey) {
        ACCESS_KEY = accessKey;
    }

    @Value("${aliyun.sms.access.secret}")
    public void setAccessSecret(String accessSecret) {
        ACCESS_SECRET = accessSecret;
    }

    @Value("${aliyun.sms.sign.text}")
    public void setSignText(String signText) {
        SIGN_TEXT = signText;
    }

    @Value("${aliyun.sms.template.code.verification}")
    public void setTemplateCodeVerification(String templateCodeVerification) {
        TEMPLATE_CODE_VERIFICATION = templateCodeVerification;
    }

    @Value("${aliyun.sms.international.template.code.verification}")
    public void setInternationalTemplateCodeVerification(String internationalTemplateCodeVerification) {
        INTERNATIONAL_TEMPLATE_CODE_VERIFICATION = internationalTemplateCodeVerification;
    }

    @Value("${aliyun.sms.message.text.verification}")
    public void setMessageTextVerification(String messageTextVerification) {
        MESSAGE_TEXT_VERIFICATION = messageTextVerification;
    }

    @Value("${market.detail.url}")
    public void setMarketDetailUrl(String marketDetailUrl) {
        MARKET_DETAIL_URL = marketDetailUrl;
    }
}
