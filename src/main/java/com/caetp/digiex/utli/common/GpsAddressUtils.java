package com.caetp.digiex.utli.common;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

/**
 * GpsAddressUtils
 */
public class GpsAddressUtils {

	/**
	 * 逆向匹配接口 根据地址名称，匹配得到经纬度坐标
	 *
	 * @param addr
	 * @return String 30.673987,104.114842
	 */
	public static Map<String, String> geocodeByGoogle(String addr) {
		try {
			addr = URLEncoder.encode(addr, "utf-8"); // url后追加中文参数乱码的问题，将中文参数用utf-8重新编码
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		String result = "";
		Map<String, String> map = new HashMap<>();
		try {
			String sUrl = "http://ditu.google.cn/maps/api/geocode/json?address={0}&sensor=false&language=zh-CN";
			result = HttpUtility.httpsGet(MessageFormat.format(sUrl, addr));

			ObjectMapper mapper = new ObjectMapper();
			JsonNode node = mapper.readTree(result.toString());

			if (node != null && node.has("results")) {
				JsonNode location = node.get("results").get(0).get("geometry").get("location");
				map.put("lat", location.get("lat").toString());
				map.put("lng", location.get("lng").toString());
			}

		} catch (Exception e) {
		}
		return map;
	}

}
