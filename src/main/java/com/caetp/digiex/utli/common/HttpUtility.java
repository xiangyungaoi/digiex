/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.caetp.digiex.utli.common;

import org.apache.http.HttpEntity;
import org.apache.http.ParseException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Iterator;
import java.util.Map;

public class HttpUtility {

	public static String httpsPost(String url, HttpEntity data) throws ParseException, IOException {
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpPost httpost = new HttpPost(url);
		// 设置请求和传输超时时间
		RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(5000).setConnectTimeout(5000).build();
		httpost.setConfig(requestConfig);
		httpost.setEntity(data);
		try {
			CloseableHttpResponse response = httpClient.execute(httpost);
			httpost.reset();
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				String result = EntityUtils.toString(entity);
				response.close();
				return result;
			}
			return null;
		} finally {
			httpClient.close();
		}
	}

	public static String httpsGet(String url) throws ParseException, IOException {
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpGet httpget = new HttpGet(url);
		try {
			CloseableHttpResponse response = httpClient.execute(httpget);
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				String result = EntityUtils.toString(entity,"utf-8");
				response.close();
				return result;
			}
			return null;
		} finally {
			httpClient.close();
		}
	}

	public static String doGet(String url, Map<String,Object> params) throws URISyntaxException, IOException {
		URIBuilder builder = new URIBuilder(url);
		Iterator var = params.entrySet().iterator();
		while(var.hasNext()) {
			Map.Entry entry = (Map.Entry) var.next();
			builder.setParameter((String) entry.getKey(),(String) entry.getValue());
		}
		return HttpUtility.httpsGet(builder.build().toString());
	}


}
