package com.shenjun.util;
/**
 * 字节工具类
 * @author shenjun
 *
 */
public class ByteUtil {
	/**
	 * 字符串转二进制
	 * @param str 特定的二进制字符串
	 * @return 返回编码过后的字符串
	 */
	public static byte[] hex2byte(String str) {
		if (str == null)
			return null;
		str = str.trim();
		int len = str.length();
		if (len == 0 || len % 2 == 1)
			return null;
		byte[] b = new byte[len / 2];
		try {

			for (int i = 0; i < str.length(); i += 2) {

				b[i / 2] = (byte) Integer
						.decode("0x" + str.substring(i, i + 2)).intValue();

			}
			return b;
		} catch (Exception e) {
			e.printStackTrace();
			return null;

		}
	}

	/**
	 * 字节转Hex字符串
	 * @param bts 字节数组
	 * @return 返回Hex字符串
	 */
	public static String bytes2Hex(byte[] bts) {
		String des = "";
		String tmp = null;

		for (int i = 0; i < bts.length; i++) {
			tmp = (Integer.toHexString(bts[i] & 0xFF));
			if (tmp.length() == 1) {
				des += "0";
			}
			des += tmp;
		}
		return des;
	}
	
	/**
	 * 转字节转成long类型
	 * @param b byte[]数组
	 * @return long
	 */
	public static long byteToLong(byte[] b) {
		long s = 0;
		long s0 = b[0] & 0xff;// 最低位
		long s1 = b[1] & 0xff;
		long s2 = b[2] & 0xff;
		long s3 = b[3] & 0xff;
		long s4 = b[4] & 0xff;// 最低位
		long s5 = b[5] & 0xff;
		long s6 = b[6] & 0xff;
		long s7 = b[7] & 0xff;

		// s0不变
		s1 <<= 8;
		s2 <<= 16;
		s3 <<= 24;
		s4 <<= 8 * 4;
		s5 <<= 8 * 5;
		s6 <<= 8 * 6;
		s7 <<= 8 * 7;
		s = s0 | s1 | s2 | s3 | s4 | s5 | s6 | s7;
		return s;
	}

	/**
	 * 转字节转成int类型
	 * @param b byte[]数组
	 * @return int
	 */
	public static int byteToInt(byte[] b) {
		int s = 0;
		int s0 = b[0] & 0xff;// 最低位
		int s1 = b[1] & 0xff;
		int s2 = b[2] & 0xff;
		int s3 = b[3] & 0xff;
		s3 <<= 24;
		s2 <<= 16;
		s1 <<= 8;
		s = s0 | s1 | s2 | s3;
		return s;
	}
	/**
	 * Int转成字节
	 * @param res
	 * @return
	 */
	public static byte[] intToByte(int res) {
		byte[] targets = new byte[4];
		targets[0] = (byte) (res & 0xff);// 最低位 
		targets[1] = (byte) ((res >> 8) & 0xff);// 次低位 
		targets[2] = (byte) ((res >> 16) & 0xff);// 次高位 
		targets[3] = (byte) (res >>> 24);// 最高位,无符号右移。 
		return targets; 
	}
}
