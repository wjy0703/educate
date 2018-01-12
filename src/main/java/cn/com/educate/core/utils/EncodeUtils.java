/**
 * Copyright (c) 2005-2009 springside.org.cn
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * 
 * $Id: EncodeUtils.java 984 2010-03-21 13:02:44Z calvinxiu $
 */
package cn.com.educate.core.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Random;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.lang.StringEscapeUtils;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;

/**
 * 各种格式的编码加码工具类.
 * 
 * 集成Commons-Codec,Commons-Lang及JDK提供的编解码方法.
 * 
 * @author calvin
 */
public class EncodeUtils {

	private static final String DEFAULT_URL_ENCODING = "UTF-8";
	public static Md5PasswordEncoder md5coder = null;

	static {
		md5coder = new Md5PasswordEncoder();
	}

	/*
	 * md5加密
	 */
	public static String getMd5PasswordEncoder(String password, String salt) {
		return md5coder.encodePassword(password, salt);
	}


	/**
	 * Hex编码.
	 */
	public static String hexEncode(byte[] input) {
		return Hex.encodeHexString(input);
	}

	/**
	 * Hex解码.
	 */
	public static byte[] hexDecode(String input) {
		try {
			return Hex.decodeHex(input.toCharArray());
		} catch (DecoderException e) {
			throw new IllegalStateException("Hex Decoder exception", e);
		}
	}

	/**
	 * Base64编码.
	 */
	public static String base64Encode(byte[] input) {
		return new String(Base64.encodeBase64(input));
	}

	/**
	 * Base64编码, URL安全(将Base64中的URL非法字符如+,/=转为其他字符, 见RFC3548).
	 */
	public static String base64UrlSafeEncode(byte[] input) {
		return Base64.encodeBase64URLSafeString(input);
	}

	/**
	 * Base64解码.
	 */
	public static byte[] base64Decode(String input) {
		return Base64.decodeBase64(input);
	}

	/**
	 * URL 编码, Encode默认为UTF-8. 
	 */
	public static String urlEncode(String input) {
		return urlEncode(input, DEFAULT_URL_ENCODING);
	}

	/**
	 * URL 编码.
	 */
	public static String urlEncode(String input, String encoding) {
		try {
			return URLEncoder.encode(input, encoding);
		} catch (UnsupportedEncodingException e) {
			throw new IllegalArgumentException("Unsupported Encoding Exception", e);
		}
	}

	/**
	 * URL 解码, Encode默认为UTF-8. 
	 */
	public static String urlDecode(String input) {
		return urlDecode(input, DEFAULT_URL_ENCODING);
	}

	/**
	 * URL 解码.
	 */
	public static String urlDecode(String input, String encoding) {
		try {
			return URLDecoder.decode(input, encoding);
		} catch (UnsupportedEncodingException e) {
			throw new IllegalArgumentException("Unsupported Encoding Exception", e);
		}
	}

	/**
	 * Html 转码.
	 */
	public static String htmlEscape(String html) {
		return StringEscapeUtils.escapeHtml(html);
	}

	/**
	 * Html 解码.
	 */
	public static String htmlUnescape(String htmlEscaped) {
		return StringEscapeUtils.unescapeHtml(htmlEscaped);
	}

	/**
	 * Xml 转码.
	 */
	public static String xmlEscape(String xml) {
		return StringEscapeUtils.escapeXml(xml);
	}

	/**
	 * Xml 解码.
	 */
	public static String xmlUnescape(String xmlEscaped) {
		return StringEscapeUtils.unescapeXml(xmlEscaped);
	}
	
	/** Encodes a password with some simple steps. */
	public static String encodePassword(String password) {
		char ca[] = password.toCharArray();
		int sl = ca.length;
		if (sl == 0)
			return "";
		char tmp;
		for (int p = 0; p < sl / 2; p++) {
			tmp = ca[p];
			ca[p] = ca[sl - p - 1];
			ca[sl - p - 1] = tmp;
		}
		StringBuffer out = new StringBuffer();
		for (int p = 0; p < sl; p++) {
			ca[p] ^= 0xaa;
			if (ca[p] < 0x10)
				out.append("0");
			out.append(Integer.toString(ca[p], 16));
		}
		// System.out.println("e1=" + out.toString());
		Random r = new Random();
		char key = (char) (r.nextInt(0xef) + 0x10);
		char outkey = key;
		ca = out.toString().toCharArray();
		sl = ca.length;
		for (int p = 0; p < sl; p++) {
			ca[p] ^= key;
			key ^= ca[p];
		}
		out = new StringBuffer();
		for (int a = 0; a < 4; a++) {
			out.append(Integer.toString(r.nextInt(0xef) + 0x10, 16));
		}
		out.append(Integer.toString(outkey, 16));
		for (int p = 0; p < sl; p++) {
			if (ca[p] < 0x10)
				out.append("0");
			out.append(Integer.toString(ca[p], 16));
		}
		for (int a = 0; a < 4; a++) {
			out.append(Integer.toString(r.nextInt(0xef) + 0x10, 16));
		}
		return out.toString();
	}

	public static String encodePassword(char password[]) {
		return encodePassword(new String(password));
	}
	
	/**
	 * 小数减法运算
	 */
	public static String doubleSubt(String fst,String sec){
		int one = (int)(Double.parseDouble(fst.trim())*1000);
		int two = (int)(Double.parseDouble(sec.trim())*1000);
		return (Double.parseDouble((one-two)+"")/1000) + "";
	}
	/**
	 * 小数加法运算
	 */
	public static String doubleAdd(String fst,String sec){
		int one = (int)(Double.parseDouble(fst.trim())*1000);
		int two = (int)(Double.parseDouble(sec.trim())*1000);
		return (Double.parseDouble((one+two)+"")/1000) + "";
	}
	/**
	 * 小数乘法运算
	 */
	public static String doubleMul(String fst,String sec){
		double one ,two;
		if(null == fst || "".equals(fst) || "null".equals(fst)){
			one = 0;
		}else{
			one = Double.parseDouble(fst.trim());
		}
		if(null == sec || "".equals(sec) || "null".equals(sec)){
			two = 0;
		}else{
			two = Double.parseDouble(sec.trim());
		}
		return (one*two)+"";
	}
	public static void main(String[] args){
		System.out.println(md5coder.encodePassword("admin", "admin"));
	}
}
