/**
 * DES加密解密类
 * @author jiangxd
 * create at 2011-11-10
 */
package cn.com.educate.core.utils;

import java.security.Key;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;

import org.apache.commons.codec.binary.Base64;

public class DesEncrypt {
	
	public static final String KEY_STRING = "asdefd";

	private Key key;

	/**
	 * 根据参数生成KEY
	 * 
	 * @param strKey
	 */
	public void getKey(String strKey) {
		try {
			KeyGenerator _generator = KeyGenerator.getInstance("DES");
			_generator.init(new SecureRandom(strKey.getBytes()));
			this.key = _generator.generateKey();
			_generator = null;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 加密String明文输入,String密文输出
	 * 
	 * @param strMing
	 * @return
	 */
	public String getEncString(String strMing) {
		byte[] byteMi = null;
		byte[] byteMing = null;
		String strMi = "";
		Base64 base64en = new Base64();
		try {
			byteMing = strMing.getBytes("UTF-8");
			byteMi = this.getEncCode(byteMing);
			strMi = base64en.encodeToString(byteMi);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			base64en = null;
			byteMing = null;
			byteMi = null;
		}
		return strMi;
	}

	/**
	 * 解密 以String密文输入,String明文输出
	 * 
	 * @param strMi
	 * @return
	 */
	public String getDesString(String strMi) {
		Base64 base64De = new Base64();
		byte[] byteMing = null;
		byte[] byteMi = null;
		String strMing = "";
		try {
			byteMi = base64De.decode(strMi);
			byteMing = this.getDesCode(byteMi);
			strMing = new String(byteMing, "UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			base64De = null;
			byteMing = null;
			byteMi = null;
		}
		return strMing;
	}

	/**
	 * 加密以byte[]明文输入,byte[]密文输出
	 * 
	 * @param byteS
	 * @return
	 */
	private byte[] getEncCode(byte[] byteS) {
		byte[] byteFina = null;
		Cipher cipher;
		try {
			cipher = Cipher.getInstance("DES");
			cipher.init(Cipher.ENCRYPT_MODE, key);
			byteFina = cipher.doFinal(byteS);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			cipher = null;
		}
		return byteFina;
	}

	/**
	 * 解密以byte[]密文输入,以byte[]明文输出
	 * 
	 * @param byteD
	 * @return
	 */
	private byte[] getDesCode(byte[] byteD) {
		Cipher cipher;
		byte[] byteFina = null;
		try {
			cipher = Cipher.getInstance("DES");
			cipher.init(Cipher.DECRYPT_MODE, key);
			byteFina = cipher.doFinal(byteD);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			cipher = null;
		}
		return byteFina;
	}

	
    /**
     * 调用DES解密类的解密方法，对加密的字符串进行解密。
     * @param encodeString
     * @return 解密后的字符串。
     * @author jiangxd
     * create at 2011-11-10
     */
    public static String decodeString(String encodeString){
    	DesEncrypt des = new DesEncrypt();
    	des.getKey(DesEncrypt.KEY_STRING);
    	return des.getDesString(encodeString);
    }

    
    /**
     * 调用DES解密类的加密方法，对字符串进行加密。
     * @param encodeString
     * @return 加密后的字符串。
     * @author jiangxd
     * create at 2011-11-10
     */
    public static String encodeString(String codeString){
    	DesEncrypt des = new DesEncrypt();
    	des.getKey(DesEncrypt.KEY_STRING);
    	return des.getEncString(codeString);
    }
    
	public static void main(String args[]) {
		DesEncrypt des = new DesEncrypt();// 实例化一个对像
		des.getKey(DesEncrypt.KEY_STRING);// 生成密匙
		String strEnc = des.getEncString("settle123");// 加密字符串,返回String的密文
		String strEnc1 = des.getEncString("zsk123");// 加密字符串,返回String的密文
		System.out.println("加密文: " + strEnc);
		System.out.println("加密文1: " + strEnc1);
		String strDes = des.getDesString(strEnc);// 把String 类型的密文解密
		System.out.println("解密文: " + strDes);
	}

	
}
