package com.caetp.digiex.utli.common;

import com.caetp.digiex.exception.CommonException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;

/**
 * TJson.java
 * Json相关工具包
 *
 */
public class TJson {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    static {
        // 解序列化时不处理未知字段
        MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    /**
     * ➜ Json对象转String
     */
    public static String objToStr(Object t) {
        try {
            return MAPPER.writeValueAsString(t);
        }catch (Exception e) {
            throw CommonException.JSON_FAIL;
        }
    }

    /**
     * ➜ String转Json对象
     * @param str
     * @param clazz 目标对象Class
     * @return
     */
    public static <T> T strToObj(String str, Class<T> clazz) {
        try {
            return MAPPER.readValue(str, clazz);
        } catch (Exception e) {
            throw CommonException.JSON_FAIL;
        }
    }

    public static <T> List<T> strToArray(String str, Class<T> clazz) {
        try {
            return MAPPER.readValue(str, MAPPER.getTypeFactory().constructCollectionType(List.class, clazz));
        } catch (Exception e) {
            throw CommonException.JSON_FAIL;
        }
    }
}
