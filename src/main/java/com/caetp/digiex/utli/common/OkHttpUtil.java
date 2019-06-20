package com.caetp.digiex.utli.common;

import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

public class OkHttpUtil {

	public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
	private static final OkHttpClient CLIENT = new OkHttpClient();

	/**
	 * 请求并返回String
	 *
	 * @param request
	 * @return
	 * @throws IOException
	 */
	public static String executeString(Request request) throws IOException {
		Response rep = CLIENT.newCall(request).execute();
		String body = rep.body().string();
		if (!rep.isSuccessful())
			throw new RuntimeException(body);
		return body;
	}

	/**
	 * 请求并解析返回Json
	 *
	 * @param request
	 * @param clazz
	 * @return
	 * @throws IOException
	 */
	public static <T> T executeJson(Request request, Class<T> clazz) throws IOException {
		Response rep = CLIENT.newCall(request).execute();
		String body = rep.body().string();
		if (!rep.isSuccessful())
			throw new RuntimeException(body);
		return new ObjectMapper().readValue(body, clazz);
	}
	
}
