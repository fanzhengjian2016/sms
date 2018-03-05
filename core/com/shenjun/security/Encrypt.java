package com.shenjun.security;

import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.shenjun.util.ByteUtil;

public class Encrypt {
	
	private static Log log = LogFactory.getLog(Encrypt.class);
	
	public final static String KEYGEN_DES="DES";
	
	public final static String KEYGEN_DESEDE="DESede";
	
	public final static String KEYGEN_BLOWFISH="Blowfish";
	
	public final static String KEYGEN_AES="AES";

	/**
	 * md5()信息摘要, 不可逆
	 * @param input
	 * @return
	 * @throws Exception
	 */
	public static byte[] md5(byte[] input) throws Exception {
		java.security.MessageDigest alg = java.security.MessageDigest
				.getInstance("MD5"); // or "SHA-1"
		alg.update(input);
		byte[] digest = alg.digest();
		return digest;
	}
	
	/**
	 * 加密
	 * @param content 需要加密的内容
	 * @param password 加密密码
	 * @param type 加密类型 可以通过Security。KEY_GEN*获取
	 * @param keySize 加密的的大小AES[128]\DES[56]\BLOWFISH[128]\DESEDE[168]
	 * @return
	 */
	public static byte[] encrypt(byte[] content, byte[] password , String type , int keySize) {
		try {
			KeyGenerator kgen = KeyGenerator.getInstance(type);
			kgen.init(keySize, new SecureRandom(password));
			SecretKey secretKey = kgen.generateKey();
			byte[] enCodeFormat = secretKey.getEncoded();
			SecretKeySpec key = new SecretKeySpec(enCodeFormat, type);
			Cipher cipher = Cipher.getInstance(type);// 创建密码器
			//byte[] byteContent = content.getBytes("utf-8");
			cipher.init(Cipher.ENCRYPT_MODE, key);// 初始化
			byte[] result = cipher.doFinal(content);
			return result; // 加密
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 解密
	 * @param content 待解密内容
	 * @param password 解密密钥
	 * @param type 加密类型 可以通过Security。KEY_GEN*获取
	 * @param keySize 加密的的大小AES[128]\DES[56]\BLOWFISH[128]\DESEDE[168]
	 * @return
	 */
	public static byte[] decrypt(byte[] content, byte[] password , String type , int keySize) {
		try {
			KeyGenerator kgen = KeyGenerator.getInstance(type);
			kgen.init(keySize, new SecureRandom(password));
			SecretKey secretKey = kgen.generateKey();
			byte[] enCodeFormat = secretKey.getEncoded();
			SecretKeySpec key = new SecretKeySpec(enCodeFormat, type);
			Cipher cipher = Cipher.getInstance(type);// 创建密码器
			
			cipher.init(Cipher.DECRYPT_MODE, key);// 初始化
			byte[] result = cipher.doFinal(content);
			return result; // 加密
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static String encryptSHA(String strSrc) { 
		MessageDigest md = null; 
		String strDes = null; 
		byte[] bt = strSrc.getBytes(); 
		try { 
			md = MessageDigest.getInstance("SHA-1"); 
			md.update(bt); 
			strDes = ByteUtil.bytes2Hex(md.digest());
			//to HexString 
		} catch(NoSuchAlgorithmException e){ 
			//System.out.println("Invalid algorithm."); 
			e.printStackTrace();
			return null;
		} 
		return strDes; 
	}

	public static void main(String[] args) {
		//Wf2/mYCstIdPnKe6spRm+w==  zilaishui6
		byte[] content = "zilaishui6".getBytes();
		byte[] password = "87654321".getBytes();

		// 加密 ASE
		System.out.println("KEYGEN_AES加密前：" + new String(content));
		byte[] encryptResult1 = encrypt(content, password,Encrypt.KEYGEN_AES,128);
		System.out.println("KEYGEN_AES加密后：" + ByteUtil.bytes2Hex(encryptResult1));
		System.out.println("KEYGEN_AES解密后：" + new String(decrypt(encryptResult1, password,Encrypt.KEYGEN_AES,128)));
		System.out.println();
		
		// 加密 KEYGEN_DES
		System.out.println("KEYGEN_DES加密前：" + new String(content));
		byte[] encryptResult2 = encrypt(content, password,Encrypt.KEYGEN_DES,56);
		
//		BASE64Decoder bdecoder = new BASE64Decoder();
//		BASE64Encoder bencoder = new BASE64Encoder();
		
		System.out.println("KEYGEN_DES加密后：" + ByteUtil.bytes2Hex(encryptResult2));
		System.out.println("KEYGEN_DES解密后：" + new String(decrypt(encryptResult2, password,Encrypt.KEYGEN_DES,56)));
		System.out.println();
		
		// 加密 KEYGEN_BLOWFISH
		System.out.println("KEYGEN_BLOWFISH加密前：" + new String(content));
		byte[] encryptResult3 = encrypt(content, password,Encrypt.KEYGEN_BLOWFISH,128);
		System.out.println("KEYGEN_BLOWFISH加密后：" + ByteUtil.bytes2Hex(encryptResult3));
		System.out.println("KEYGEN_BLOWFISH解密后：" + new String(decrypt(encryptResult3, password,Encrypt.KEYGEN_BLOWFISH,128)));
		System.out.println();
		
		// 加密 KEYGEN_DESEDE
		System.out.println("KEYGEN_DESEDE加密前：" + new String(content));
		byte[] encryptResult4 = encrypt(content, password,Encrypt.KEYGEN_DESEDE,168);
		System.out.println("KEYGEN_DESEDE加密后：" + ByteUtil.bytes2Hex(encryptResult4));
		System.out.println("KEYGEN_DESEDE解密后：" + new String(decrypt(encryptResult4, password,Encrypt.KEYGEN_DESEDE,168)));
		System.out.println();
		

	}

}
