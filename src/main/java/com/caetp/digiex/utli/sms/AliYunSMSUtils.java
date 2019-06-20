package com.caetp.digiex.utli.sms;

import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.caetp.digiex.consts.StaticProperties;

/**
 * Created by wangzy on 2018/6/1.
 * 阿里云短信服务
 */
public class AliYunSMSUtils {

    public static String ACCESS_KEY = StaticProperties.ACCESS_KEY;
    public static String ACCESS_SECRET = StaticProperties.ACCESS_SECRET;
    public static String SIGN_TEXT = StaticProperties.SIGN_TEXT;

    public static String TEMPLATE_CODE_VERIFICATION = StaticProperties.TEMPLATE_CODE_VERIFICATION;
    public static String INTERNATIONAL_TEMPLATE_CODE_VERIFICATION = StaticProperties.INTERNATIONAL_TEMPLATE_CODE_VERIFICATION;
    public static String MESSAGE_TEXT_VERIFICATION = StaticProperties.MESSAGE_TEXT_VERIFICATION;

    /**
     * 发送验证码
     * @param mobile
     * @param accessKey
     * @param accessSecret
     * @param signText
     * @param templateCode
     * @param messageText
     * @param code
     * @return
     */
    public static boolean sendCode(String mobile, String accessKey, String accessSecret, String signText,
                                              String templateCode, String messageText, String code){
        IClientProfile profile = DefaultProfile.getProfile("cn-shenzhen", accessKey,
                accessSecret);
        try {
            DefaultProfile.addEndpoint("cn-shenzhen", "cn-shenzhen", "Dysmsapi", "dysmsapi.aliyuncs.com");
            IAcsClient client = new DefaultAcsClient(profile);
            SendSmsRequest request = new SendSmsRequest();
            request.setSignName(signText);
            request.setTemplateCode(templateCode);
            JSONObject json = new JSONObject();
            json.put(messageText, code);
            request.setTemplateParam(json.toString());
            request.setPhoneNumbers(mobile);
            SendSmsResponse httpResponse = client.getAcsResponse(request);
            if (null != httpResponse.getCode() && httpResponse.getCode().equals("OK")) {
                System.out.println("发送成功");
                return true;
            }
        } catch (ServerException e) {
            e.printStackTrace();
            return false;
        } catch (ClientException e) {
            e.printStackTrace();
            return false;
        }
        return false;
    }

    /**
     * 发送验证码
     * @param mobile
     * @param code
     * @return
     */
    public static boolean sendCodeVerification(String mobile, String code) {
        return sendCode(mobile, ACCESS_KEY, ACCESS_SECRET, SIGN_TEXT,
                TEMPLATE_CODE_VERIFICATION, MESSAGE_TEXT_VERIFICATION, code);
    }

    /**
     * 发送国际短信验证码
     * @param mobile
     * @param code
     * @return
     */
    public static boolean sendInternationalCodeVerification(String mobile, String code) {
        return sendCode(mobile, ACCESS_KEY, ACCESS_SECRET, SIGN_TEXT,
                INTERNATIONAL_TEMPLATE_CODE_VERIFICATION, MESSAGE_TEXT_VERIFICATION, code);
    }

    public static void main(String[] args) {
      /*  String mobile = "18377302284";
        String code = "1234";
        if (sendCodeVerification(mobile, code)) {
            System.out.println("发送成功");
        } else {
            System.out.println("发送失败");
        }
    }*/

        DefaultProfile profile = DefaultProfile.getProfile("cn-shenzhen", "LTAI2GTcF10FE6Bg",
                "eO0TZHMn7x0gDkHE16bEzMQO2NBp0a");
        IAcsClient client = new DefaultAcsClient(profile);

        CommonRequest request = new CommonRequest();
        request.setMethod(MethodType.POST);
        request.setDomain("dysmsapi.aliyuncs.com");
        request.setVersion("2017-05-25");
        request.setAction("SendSms");
        request.putQueryParameter("PhoneNumbers", "18377302284");
        request.putQueryParameter("SignName", "DigiEx");
        request.putQueryParameter("TemplateCode", "SMS_136255028");
        request.putQueryParameter("TemplateParam", "{code:123}");
        try {
            CommonResponse response = client.getCommonResponse(request);
            System.out.println(response.getData());
        } catch (ServerException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            e.printStackTrace();
        }
    }
}
