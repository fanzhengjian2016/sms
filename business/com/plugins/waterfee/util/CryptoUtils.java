package com.plugins.waterfee.util;

import java.nio.charset.Charset;
import java.security.Key;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;

import com.shenjun.util.ByteUtil;

public class CryptoUtils {
	public final static byte[] keyBytes = { 0x11, 0x22, 0x4F, 0x58,
			(byte) 0x88, 0x10, 0x40, 0x38, 0x28, 0x25, 0x79, 0x51, (byte) 0xCB,
			(byte) 0xDD, 0x55, 0x66, 0x77, 0x29, 0x74, (byte) 0x98, 0x30, 0x40,
			0x36, (byte) 0xE2 };
	
	final static public String DES_TRANS = "DESede/ECB/PKCS5Padding"; // 加密算法

	/**
	 * ECB加密
	 * 
	 * @param key
	 *            密钥
	 * @param data
	 *            明文
	 * @return Base64编码的密文
	 * @throws Exception
	 */
	public static byte[] encodeECB(byte[] key, byte[] data) throws Exception {
		Key deskey = null;
		DESedeKeySpec spec = new DESedeKeySpec(key);
		SecretKeyFactory keyfactory = SecretKeyFactory.getInstance("DESede");
		deskey = keyfactory.generateSecret(spec);
		Cipher cipher = Cipher.getInstance(DES_TRANS);
		cipher.init(Cipher.ENCRYPT_MODE, deskey);
		byte[] bOut = cipher.doFinal(data);
		return bOut;
	}

	/**
	 * ECB解密
	 * 
	 * @param key
	 *            密钥
	 * @param data
	 *            Base64编码的密文
	 * @return 明文
	 * @throws Exception
	 */
	public static byte[] decodeECB(byte[] key, byte[] data) throws Exception {
		Key deskey = null;
		DESedeKeySpec spec = new DESedeKeySpec(key);
		SecretKeyFactory keyfactory = SecretKeyFactory.getInstance("DESede");
		deskey = keyfactory.generateSecret(spec);
		Cipher cipher = Cipher.getInstance(DES_TRANS);
		cipher.init(Cipher.DECRYPT_MODE, deskey);
		byte[] bOut = cipher.doFinal(data);
		return bOut;
	}
	
	public static String encodeTo64(byte[]aa){
		return new sun.misc.BASE64Encoder().encode(aa);
	}
	
	public static byte[] decodeTo64(String str) throws Exception{
		byte [] a=new sun.misc.BASE64Decoder().decodeBuffer(str);
		return a;
	}

	public static void main(String[] args) throws Exception {
		/**
		 * KEYGEN_DESEDE加密前：test KEYGEN_DESEDE加密后：499b979e85898640
		 * KEYGEN_DESEDE解密后：test
		 */
		byte[] encryptResult = CryptoUtils.encodeECB("天天向上天天向上天天向上".getBytes(),"tes洪建华t".getBytes());

		System.out.println("KEYGEN_DESEDE加密后：" + ByteUtil.bytes2Hex(encryptResult));
		
		//System.out.println(new String(CryptoUtils.decodeECB("longshinetozhongshanwtzs".getBytes(),"kWWjc/UHAoVe8VTesOTVLqCnuqRvF+mafSnFRDU1MgECzreRURQZwJqKUID0r8ipctqDA6l0R2tKGfHRG/fr1Svz6vYm5TOmUfJ/WDyMeAzoiQKMM23E6t561yxTN9sUhB92PaTw/tVGRpgW3e6HZ9UKc6pyrJRzJXu8d2X8aY0U+Kn7nEBRGWdrI9g1/NWqunGQQuhvuAZV+E9OspZTbAX85cTjZWd5DqsXLtWD822AaINZ/RlcXyGc0x9sCmdZ9DWR8TKrKVn86ilT7AynRXALmUfbIiqZQFzFxk/KcNrZ9xoaaXBHQ6QZ7O8gTlB6DQbbLEb5O004177jh5Zqrl4+DZDx/PRvAtBWqIG3y56ZHpzkvh31C4tl4Fg97qIpw1MV/4y4Pdk=".getBytes())));
		// 加密 KEYGEN_DESEDE

		
		
//		byte b = 0;System.out.println(" "+0xff+0x98);
//		for ( int i = 0; i <= 0xff; ++i ) 
//		{ 
//			b = (byte)i; 
//			System.out.print(" "+i+":"+(b&0xff)+", ");
//			}
//		
//		char x = '我'; 
//
//		String str = "a"; 
//		
//		System.out.println(str.getBytes("utf-8").length);
		byte s=(byte) 0X08;
		System.out.println(java.lang.Integer.toHexString(s & 0XFF));
		
		
		String key="longshinetozhongshanwtzs";
		
		String data="kWWjc/UHAoVe8VTesOTVLqCnuqRvF+mafSnFRDU1MgECzreRURQZwJqKUID0r8ipctqDA6l0R2tKGfHRG/fr1Svz6vYm5TOmUfJ/WDyMeAzoiQKMM23E6t561yxTN9sUhB92PaTw/tVGRpgW3e6HZ9UKc6pyrJRzJXu8d2X8aY0U+Kn7nEBRGWdrI9g1/NWqunGQQuhvuAZV+E9OspZTbAX85cTjZWd5DqsXLtWD822AaINZ/RlcXyGc0x9sCmdZ9DWR8TKrKVn86ilT7AynRXALmUfbIiqZQFzFxk/KcNrZ9xoaaXBHQ6QZ7O8gTlB6DQbbLEb5O004177jh5Zqrl4+DZDx/PRvAtBWqIG3y56ZHpzkvh31C4tl4Fg97qIpw1MV/4y4Pdk=";
		
		
		
		byte[]bb=CryptoUtils.decodeECB(key.getBytes(), new sun.misc.BASE64Decoder().decodeBuffer(data));
		System.out.println(new String(bb));
		
		System.out.println(new String(CryptoUtils.decodeTo64(CryptoUtils.encodeTo64(key.getBytes()))));
		
		 String csn = Charset.defaultCharset().name();
		 System.out.println(csn);
	}
}
