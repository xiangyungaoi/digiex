package com.caetp.digiex.utli.common;

import com.alibaba.fastjson.JSON;

import java.util.Map;

/**
 * Created by gaoyx on 2019/5/16.
 */

public class GetMemberId {

    public static String getMemberId(String reqParameter){
        Map map = (Map) JSON.parseObject(reqParameter);
        // 拿到reqid ,从中取出memberid
        String reqid = (String) map.get("reqid");
        String[] reqids = reqid.split("\\.");
        // reqids[0]--> memberid
       return reqids[0];
    }

    public static String getMebmerId(Map reqParamererMap) {
        // 拿到reqid ,从中取出memberid
        String reqid = (String) reqParamererMap.get("reqid");
        String[] reqids = reqid.split("\\.");
        // reqids[0]--> memberid
        return reqids[0];
    }
}
