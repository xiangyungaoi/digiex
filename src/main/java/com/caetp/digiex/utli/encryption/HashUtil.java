package com.caetp.digiex.utli.encryption;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

public class HashUtil {

	/**
	 * ➜ hmac散列获取
	 * @param msg 待计算的字符串
	 * @param keyString key
	 * @param algo 算法,如HMACMD5,HMACSHA1,HMACSHA224,HMACSHA256,HMACSHA384,HMACSHA512,PBKDF2
	 * @return
	 */
	public static String hmac(String msg, String keyString, String algo) {
		String digest = null;
		try {
			SecretKeySpec key = new SecretKeySpec(keyString.getBytes("UTF-8"), algo);
			Mac mac = Mac.getInstance(algo);
			mac.init(key);

			byte[] bytes = mac.doFinal(msg.getBytes("ASCII"));

			StringBuffer hash = new StringBuffer();
			for (int i = 0; i < bytes.length; i++) {
				String hex = Integer.toHexString(0xFF & bytes[i]);
				if (hex.length() == 1) {
					hash.append('0');
				}
				hash.append(hex);
			}
			digest = hash.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return digest;
	}


}
