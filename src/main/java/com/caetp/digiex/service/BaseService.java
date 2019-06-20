package com.caetp.digiex.service;

import com.caetp.digiex.consts.ProjectConsts;
import com.caetp.digiex.entity.Member;
import com.caetp.digiex.entity.mapper.MemberMapper;
import com.caetp.digiex.exception.UserException;
import com.caetp.digiex.utli.common.Utility;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class BaseService {

	@Autowired
	private MemberMapper memberMapper;


	/**
	 * 验证用户token
	 * @param token
	 * @return
	 */
	protected Member validateUserToken(String token) {
		List<Member> list = memberMapper.listUserByToken(token);
		if (list.size() != 1) {
			throw UserException.TOKEN_ERROR;
		}
		return list.get(0);
	}


	/**
	 * 密码加密
	 * @param password
	 * @return
	 */
	protected static String encryptPassword(String password) {
		return Hex.encodeHexString(DigestUtils.sha256(password));
	}

	/**
	 * 通过userId获取token
	 * @param userId
	 * @return
	 */
	protected static String getEncToken(long userId) {
		String tokenKey = Utility.getRandomString(10);
		String base64 = userId + ProjectConsts.VERTICAL_LINE + tokenKey + ProjectConsts.VERTICAL_LINE + System.currentTimeMillis();
		return new String(Base64.encodeBase64(base64.getBytes()));
	}

	protected static Long getUserIdByToken(String token) {
		if (token == null || token.isEmpty()) {
			return 0L;
		}
		String tokenStr = "";
		try {
			tokenStr = new String(Base64.decodeBase64(token), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		if (tokenStr.isEmpty() || tokenStr.split("\\|").length == 0) {
			return 0L;
		}
		return Long.valueOf(tokenStr.split("\\|")[0]);
	}

	/**
	 * 获取请求参数中所有的信息
	 *
	 */
	protected static Map<String, String> getAllRequestParam(final HttpServletRequest request) {
		Map<String, String> reqParam = new HashMap<>();
		Enumeration<?> temp = request.getParameterNames();
		if (null != temp) {
			while (temp.hasMoreElements()) {
				String en = (String) temp.nextElement();
				String value = request.getParameter(en);
				reqParam.put(en, value);
				if (null == reqParam.get(en) || "".equals(reqParam.get(en))) {
					reqParam.remove(en);
				}
			}
		}
		return reqParam;
	}

	public static void main(String[] args) {
		// System.out.println(BaseService.encryptPassword("Aa123456"));
		System.out.println(getEncToken(1));
		System.out.println(getUserIdByToken(getEncToken(1)));
	}

}
