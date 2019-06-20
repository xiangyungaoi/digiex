package com.caetp.digiex.utli.common;

import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.caetp.digiex.consts.ProjectConsts;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class Utility {
	private static final String AB = "123456789abcdefghijklmnpqrstuvwxyz";
	private static final String[] chars = new String[] { "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p",
			"q", "r", "s", "t", "u", "v", "w", "x", "y", "z", "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "A", "B", "C", "D", "E",
			"F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z" };
	public static String DATA_STORE = "";
	private static Logger logger = LoggerFactory.getLogger(Utility.class);
	private static Random rnd = new Random();

	public static String generateShortUuid() {
		StringBuffer shortBuffer = new StringBuffer();
		String uuid = UUID.randomUUID().toString().replace("-", "");
		for (int i = 0; i < 8; i++) {
			String str = uuid.substring(i * 4, i * 4 + 4);
			int x = Integer.parseInt(str, 16);
			shortBuffer.append(chars[x % 0x3E]);
		}
		return shortBuffer.toString();

	}

	/**
	 * 针对微信支付生成商户订单号，为了避免微信商户订单号重复（下单单位支付），
	 * @return
	 */
	public static String generateOrderSN() {
		StringBuffer orderSNBuffer = new StringBuffer();
		orderSNBuffer.append(System.currentTimeMillis());
		orderSNBuffer.append(getRandomString(7));
		return orderSNBuffer.toString();
	}

	public static String generateUuid() {
		String uuid = UUID.randomUUID().toString().replace("-", "");
		return uuid;
	}

	public static boolean contains(String[] array, String item) {
		try {
			for (int i = 0; i < array.length; i++) {
				if (array[i].equals(item)) {
					return true;
				}
			}
		} catch (Exception ex) {
			logger.error(ex.getMessage());
		}
		return false;
	}

	public static String normalizePath(String path) {
		return path.replace("\\\\", "\\").replace("\\", "/").replace("//", "/").replace("..", ".");
	}

	public static String[] rangeSplit(String origin) {
		// origin should like "1-5"
		String[] s = origin.split("-");
		if (s.length != 2) {
			throw new IllegalArgumentException();
		}
		return s;
	}

	public static String getRandomString(int len) {
		StringBuilder sb = new StringBuilder(len);
		for (int i = 0; i < len; i++)
			sb.append(AB.charAt(rnd.nextInt(AB.length())));
		return sb.toString();
	}

	public static String getRandomCode(int len) {
		StringBuilder sb = new StringBuilder(len);
		for (int i = 0; i < len; i++)
			sb.append("0123456789".charAt(rnd.nextInt("0123456789".length())));
		return sb.toString();
	}

	// 阿里云短信服务
	public static String sendCodeAsAliYunSMS(String mobile, Map<String, Object> map, String accessKey, String accessSecret,
											 String signText, String templateCode, String messageText){

		IClientProfile profile = DefaultProfile.getProfile("cn-shenzhen-nanshan", accessKey,
				accessSecret);
		try {
			DefaultProfile.addEndpoint("cn-shenzhen-nanshan", "cn-shenzhen-nanshan", "Dysmsapi", "dysmsapi.aliyuncs.com");
			IAcsClient client = new DefaultAcsClient(profile);
			SendSmsRequest request = new SendSmsRequest();
			String code = getRandomCode(6);
			request.setSignName(signText);
			request.setTemplateCode(templateCode);
			JSONObject json = new JSONObject();
			json.put(messageText, code);
			request.setTemplateParam(json.toString());
			request.setPhoneNumbers(mobile);
			SendSmsResponse httpResponse = client.getAcsResponse(request);
			if (null != httpResponse.getCode() && httpResponse.getCode().equals("OK")) {
				map.put("statusCode", 0);
				return code;
			}
		} catch (ServerException e) {
			map.put("statusCode", 400);
			map.put("msg", e.getErrMsg());
		} catch (ClientException e) {
			map.put("statusCode", 400);
			map.put("msg", e.getErrMsg());
		}
		return null;
	}

	public static String saveFile(MultipartFile file, String rootPath, String path) {
		File _localDirectory = new File(rootPath + path);
		if (!_localDirectory.exists()) {
			_localDirectory.mkdirs();
		}
		File outPutFile = new File(_localDirectory, System.currentTimeMillis() + getRandomString(4) +
				file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".")));
		try {
			FileUtils.copyInputStreamToFile(file.getInputStream(), outPutFile);
			return path + outPutFile.getName();
		} catch (IOException e) {
			logger.error(e.getMessage());
			return null;
		}
	}

	public static String saveFile(File file,String rootPath, String path) {
		File _localDirectory = new File(rootPath + path);
		if (!_localDirectory.exists()) {
			_localDirectory.mkdirs();
		}
		File outputfile = new File(_localDirectory,
				System.currentTimeMillis() + ProjectConsts.UNDER_LINE + file.getName());
		try {
			FileUtils.copyInputStreamToFile(new FileInputStream(file), outputfile);
			return path + outputfile.getName();
		} catch (IOException e) {
			logger.error(e.getMessage());
			return null;
		}
	}
	public static String saveFile(long time,File file,String rootPath, String path) {
		File _localDirectory = new File(rootPath + path);
		if (!_localDirectory.exists()) {
			_localDirectory.mkdirs();
		}
		File outputfile = new File(_localDirectory,
				time + ProjectConsts.UNDER_LINE + file.getName());
		try {
			FileUtils.copyInputStreamToFile(new FileInputStream(file), outputfile);
			return path + outputfile.getName();
		} catch (IOException e) {
			logger.error(e.getMessage());
			return null;
		}
	}
	
	public static String readFile(InputStream in) {
		BufferedReader reader = null;
		String result = "";
		try {
			InputStreamReader inputStreamReader = new InputStreamReader(in, "UTF-8");
			reader = new BufferedReader(inputStreamReader);
			String tempString = null;
			while ((tempString = reader.readLine()) != null) {
				result += tempString;
			}
			reader.close();
		} catch (IOException e) {
			logger.error(e.getMessage());
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					logger.error(e.getMessage());
				}
			}
		}
		return result;
	}

	public static int getCurrentMonthLastDay() {
		Calendar a = Calendar.getInstance();
		a.set(Calendar.DATE, 1);// 把日期设置为当月第一天
		a.roll(Calendar.DATE, -1);// 日期回滚一天，也就是最后一天
		int maxDate = a.get(Calendar.DATE);
		return maxDate;
	}

	public static String getOrderSn(Long id) {
		if (id == null)
			throw new NullPointerException("id can not be null");

		SimpleDateFormat format = new SimpleDateFormat("yyMMdd");
		String date = format.format(new Date());
		String sn = String.format("%0" + 5 + "d", id);
		return "LTR" + date + sn;
	}

	public static String formatDateToString(String format, Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(date);
	}

	public static Date formatStringToDate(String format, String dateStr) {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		Date date = null;
		try {
			date = sdf.parse(dateStr);
		} catch (ParseException e) {
			e.printStackTrace();
			date = new Date();
		}
		return date;
	}

	public static String getIp(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}

	/**
     * 检查型异常转换
     * @author Yin
     * @param e
     * @return
     */
    public static RuntimeException wrap(Throwable e) {
        if (e instanceof RuntimeException) {
            return (RuntimeException) e;
        }
        return new RuntimeException(e);
    }

}
