package com.shenjun.util;

import java.util.Arrays;

public class ArraysUtil {
	/**
	 * 合并数组
	 * @param first
	 * @param rest
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T[] concatAll(T[] first, T[]... rest) {
		int totalLength = first.length;
		for (T[] array : rest) {
			totalLength += array.length;
		}
		T[] result = Arrays.copyOf(first, totalLength);
		int offset = first.length;
		for (T[] array : rest) {
			System.arraycopy(array, 0, result, offset, array.length);
			offset += array.length;
		}
		return result;
	}
}
