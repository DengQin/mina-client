package com.dengqin.mina.utils;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * Created by dq on 2017/9/22.
 */
public class StringUtils {

	private static final String RANDOM_CHARS = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";

	/**
	 * 获得一个随机的字符串
	 *
	 * @param len
	 * @return
	 */
	public static String getRandomString(int len) {
		StringBuilder buf = new StringBuilder(len + 1);

		Random rand = new Random();
		for (int i = 0; i < len; i++) {
			buf.append(RANDOM_CHARS.charAt(rand.nextInt(RANDOM_CHARS.length())));
		}
		return buf.toString();
	}

	/**
	 * 首字母大写
	 *
	 * @param str
	 * @return
	 */
	public static String upCaseFirst(String str) {
		char[] arr = str.toCharArray();
		arr[0] = Character.toUpperCase(arr[0]);
		return new String(arr);
	}

	public static String trim(String str) {
		if (str == null) {
			return "";
		}
		return str.trim();
	}

	/**
	 * 检查空字符串，或者" "字符串
	 *
	 * @param str
	 * @return
	 */
	public static boolean isBlank(String str) {
		int strLen;
		if (str == null || (strLen = str.length()) == 0) {
			return true;
		}
		for (int i = 0; i < strLen; i++) {
			if ((Character.isWhitespace(str.charAt(i)) == false)) {
				return false;
			}
		}
		return true;
	}

	public static boolean isNotBlank(String str) {
		int strLen;
		if (str == null || (strLen = str.length()) == 0) {
			return false;
		}
		for (int i = 0; i < strLen; i++) {
			if ((Character.isWhitespace(str.charAt(i)) == false)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 从s1中截取s2和s3中间的字符串,找不到返回""
	 *
	 * @param s1
	 *            源字符串
	 * @param s2
	 *            开始字符串
	 * @param s3
	 *            结束字符串
	 * @return
	 */
	public static String getBetweenString(String s1, String s2, String s3) {
		char[] arr1 = s1.toCharArray();
		char[] arr2 = s2.toCharArray();
		char[] arr3 = s3.toCharArray();
		boolean isFoundHead = false;
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < arr1.length; i++) {
			if (arr1[i] == arr2[0] && isMatch(arr1, i, arr2) && !isFoundHead) {// 找到开头
				i += arr2.length;
				isFoundHead = true;
			}
			if (isFoundHead) {
				if (arr1[i] == arr3[0] && isMatch(arr1, i, arr3)) {// 找到结尾
					i += arr3.length - 1;
					return sb.toString().trim();
				} else {
					sb.append(arr1[i]);
				}
			}
		}
		return "";
	}

	// 从arr1的start匹配arr2
	public static boolean isMatch(char[] arr1, int start, char[] arr2) {
		for (int j = 0; j < arr2.length && start + j < arr1.length; j++) {
			if (arr1[start + j] != arr2[j]) {
				return false;
			}
		}
		return true;
	}

	public static Object convert(String s, Class<?> cls, String dateFormat) throws ParseException {
		if (s == null) {
			throw new RuntimeException("要转换的参数为空");
		}
		if (cls.equals(String.class)) {
			return s;
		} else if (cls.equals(int.class) || cls.equals(Integer.class)) {
			return Integer.parseInt(s);
		} else if (cls.equals(double.class) || cls.equals(Double.class)) {
			return Double.parseDouble(s);
		} else if (cls.equals(long.class) || cls.equals(Long.class)) {
			return Long.parseLong(s);
		} else if (cls.equals(Date.class)) {
			if (isBlank(dateFormat)) {
				dateFormat = "yyyy-MM-dd HH:mm:ss";
			}
			return new SimpleDateFormat(dateFormat).parse(s);
		} else if (cls.equals(BigDecimal.class)) {
			return new BigDecimal(s);
		} else if (cls.equals(short.class) || cls.equals(Short.class)) {
			return Short.parseShort(s);
		} else if (cls.equals(boolean.class) || cls.equals(Boolean.class)) {
			if (isBlank(s)) {
				return false;
			}
			s = s.trim();
			if ("1".equals(s) || "true".equalsIgnoreCase(s)) {
				return true;
			}
			return false;
		}
		throw new RuntimeException("不支持类型" + cls.getName());
	}

	public static String getFileType(String fileName) {
		if (fileName == null) {
			return null;
		}
		int index = fileName.lastIndexOf(".");
		if (index < 0) {
			return null;
		}
		return fileName.substring(index);
	}

	/**
	 * 将字符串按逗号分割、去空格，并放入到ArrayList里面
	 * 
	 * @param str
	 * @return
	 */
	public static List<String> convertToList(String str) {
		String[] s = StringUtils.trim(str).split(",");
		List<String> list = new ArrayList<String>();
		for (String st : s) {
			list.add(StringUtils.trim(st));
		}
		return list;
	}
}
