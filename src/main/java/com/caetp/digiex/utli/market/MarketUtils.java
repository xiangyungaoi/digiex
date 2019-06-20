package com.caetp.digiex.utli.market;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 行情工具类
 *
 */
public class MarketUtils {

	public static String host = "http://finance.api51.cn";
	public static String appcode = "5a5fd24e1c9f4d9c9da22aa59496a246";
	public static String method = "GET"; // 请求方式


	/**
	 * 获取实时行情
	 */
	public static Map<String, Double> getRealInfo(String pairs) {
		String path = "/forex_real";
		Map<String, String> headers = new HashMap<>();
		headers.put("Authorization", "APPCODE " + appcode);
		Map<String, String> querys = new HashMap<>();
		querys.put("pairs", pairs);
		Map<String, Double> realMarket = new HashMap<>();
		try {
			HttpResponse response = HttpUtils.doGet(host, path, method, headers, querys);
			String entitys = EntityUtils.toString(response.getEntity());
			List<JSONObject> markets = JSONObject.parseArray(entitys, JSONObject.class);
			for (JSONObject market : markets) {
				realMarket.put(market.getString("symbol"), market.getDouble("price"));
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return realMarket;
	}


	/**
	 * @return 获取k线图数据
	 */
	public static List<JSONObject> getChartInfo(String pairs, String count, String type) {
		List<JSONObject> markets;
		String path = "/forex_chart";
		Map<String, String> headers = new HashMap<>();
		headers.put("Authorization", "APPCODE " + appcode);
		Map<String, String> querys = new HashMap<>();
		querys.put("pairs", pairs);
		querys.put("count", count);
		querys.put("type", type); // 间隔周期
		try {
			HttpResponse response = HttpUtils.doGet(host, path, method, headers, querys);
			String result = EntityUtils.toString(response.getEntity());
			markets = JSONObject.parseArray(result, JSONObject.class);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return markets;
	}


	public static void main(String[] str) {
		System.out.println(MarketUtils.getRealInfo("EURUSD,GBPUSD,USDJPY,USDCHF,AUDUSD,USDCAD,AUDUSD,EURAUD,USDCNH"));
		// System.out.println(getChartInfo("EURUSD", "100", "1"));
	}

}
