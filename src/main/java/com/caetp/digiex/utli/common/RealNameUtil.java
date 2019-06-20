package com.caetp.digiex.utli.common;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.caetp.digiex.consts.ProjectConsts;
import com.caetp.digiex.consts.RegexConsts;
import okhttp3.Request;
import org.springframework.util.StringUtils;

import java.text.MessageFormat;
import java.util.regex.Pattern;

/**
 * Created by wangzy on 2018/9/18.
 */
public class RealNameUtil {

    public static final String APP_CODE="5a5fd24e1c9f4d9c9da22aa59496a246";

    /**
     * 校验身份证
     *
     * @param idCard
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isIDCard(String idCard) {
        if (StringUtils.isEmpty(idCard)) {
            return false;
        }
        return RegexConsts.VALID_CHINA_IDENTITY_CARD.matcher(idCard).matches();
    }

    /**
     * 身份证信息实名认证
     * @param name
     * @param idNo
     * @return
     */
    public static boolean realNameAuth(String name, String idNo) {
        boolean auth = false;

        try {
           String url = MessageFormat.format(ProjectConsts.IDEN_AUTHEN_URL, name, idNo);
            Request request = new Request.Builder()
                    .url(url)
                    .header(ProjectConsts.AUTHORIZATION,
                            MessageFormat.format(ProjectConsts.AUTHORIZATION_HEADER, APP_CODE))
                    .build();
            String resultStr = OkHttpUtil.executeString(request);
            JSONObject jsonObject = JSON.parseObject(resultStr);
            auth = jsonObject.getJSONObject(ProjectConsts.RESULT).getBoolean(ProjectConsts.IS_OK);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return auth;
    }

    public static void main(String[] args) {
        // System.out.println(realNameAuth("王志远", "431224199112215414"));
        System.out.println(isIDCard("431224199112215414"));
    }
}
