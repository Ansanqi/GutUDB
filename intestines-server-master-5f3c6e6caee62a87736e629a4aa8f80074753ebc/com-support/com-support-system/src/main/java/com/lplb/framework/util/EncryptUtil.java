package com.lplb.framework.util;

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 加密工具
 * 
 * @author <a href="mailto:yuanhuiwu@gmail.com">Huiwu Yuan</a>
 * @since 1.0
 */
public class EncryptUtil {

	private static final Logger log = LoggerFactory.getLogger(EncryptUtil.class);
	/**
	 * 
	 */
	private static final String cvt = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
			+ "abcdefghijklmnopqrstuvwxyz0123456789#@$";

	/**
	 * 
	 */
	private static final int fillchar = '*';

	/**
	 *	数据库连接信息密文处理
	 * @param content 加解密内容
	 * @param password jasypt密钥
	 * @param type 类型 1=加密  2=解密
	 * @return
	 */
	public static String jasyptDataBase(String content,String password,String type){
		StandardPBEStringEncryptor standardPBEStringEncryptor = new StandardPBEStringEncryptor();
		standardPBEStringEncryptor.setAlgorithm("PBEWithMD5AndDES");
		standardPBEStringEncryptor.setPassword(password);
		if("1".equals(type)){
			return standardPBEStringEncryptor.encrypt(content);
		}else{
			return standardPBEStringEncryptor.decrypt(content);
		}
	}

	public static void main(String[] args) {
		System.out.println(jasyptDataBase("dearloser70","vh^onsYFUx^DMCKK","1"));
	}

	/**
	 * 加密
	 * 
	 * @param str
	 *            明文
	 * @return 密文
	 */
	public static String encrypt(String str) {
		byte[] data = str.getBytes();
		int c;
		int len = data.length;
		StringBuilder ret = new StringBuilder(((len / 3) + 1) * 4);
		for (int i = 0; i < len; ++i) {
			c = (data[i] >> 2) & 0x3f;
			ret.append(cvt.charAt(c));
			c = (data[i] << 4) & 0x3f;
			if (++i < len) {
				c |= (data[i] >> 4) & 0x0f;
			}
			ret.append(cvt.charAt(c));
			if (i < len) {
				c = (data[i] << 2) & 0x3f;
				if (++i < len) {
					c |= (data[i] >> 6) & 0x03;
				}
				ret.append(cvt.charAt(c));
			} else {
				++i;
				ret.append((char) fillchar);
			}
			if (i < len) {
				c = data[i] & 0x3f;
				ret.append(cvt.charAt(c));
			} else {
				ret.append((char) fillchar);
			}
		}
		return ret.toString();
	}

	/**
	 * 解密
	 * 
	 * @param str
	 *            密文
	 * @return 明文
	 */
	public static String decrypt(String str) {
		byte[] data = str.getBytes();
		int c, c1;
		int len = data.length;
		StringBuilder ret = new StringBuilder((len * 3) / 4);
		for (int i = 0; i < len; ++i) {
			c = cvt.indexOf(data[i]);
			++i;
			c1 = cvt.indexOf(data[i]);
			c = ((c << 2) | ((c1 >> 4) & 0x3));
			ret.append((char) c);
			if (++i < len) {
				c = data[i];
				if (fillchar == c) {
					break;
				}
				c = cvt.indexOf((char) c);
				c1 = ((c1 << 4) & 0xf0) | ((c >> 2) & 0xf);
				ret.append((char) c1);
			}
			if (++i < len) {
				c1 = data[i];
				if (fillchar == c1) {
					break;
				}
				c1 = cvt.indexOf((char) c1);
				c = ((c << 6) & 0xc0) | c1;
				ret.append((char) c);
			}
		}
		return ret.toString();
	}
}
