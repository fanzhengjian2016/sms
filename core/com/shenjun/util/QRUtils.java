package com.shenjun.util;

import net.glxn.qrgen.QRCode;
import net.glxn.qrgen.image.ImageType;


/**
 * 二维码操作类
 * @author shenjun
 *
 */
public class QRUtils {
	/**
	 * 通过指定的字符串生成二维码
	 * @param text
	 * @param width
	 * @param height
	 * @return 图片的二制进字节数据
	 */
	public static byte[] createQRImg(String text,int width,int height){
		return QRCode.from(text).withSize(width, height).to(ImageType.PNG).stream().toByteArray();
	}
	
	/*public static void main(String[] args) throws IOException {
		File f = new java.io.File("c:/MyFile/temp/qrgen.jpg");
		FileOutputStream fos = new FileOutputStream(f);
		fos.write(QRUtils.createQRImg("http://ddagfsfsdfsfhslfjsfjskfjksndkjfsdjfsdjkjfskdjj",125,125));
		fos.flush();fos.close();
	}*/
}
