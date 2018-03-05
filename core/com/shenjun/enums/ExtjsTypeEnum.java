package com.shenjun.enums;

public enum ExtjsTypeEnum {
	/**
	 * //Store 类型的多数组结构
	 */
	MULTI_DATA_STORE,
	/**
	 * store 分页结构，需要指定GID。不指定GID，程序与抛出异常
	 */
	MULTI_SPLIT_STORE,
	/**
	 * //json 类型的单数据结构
	 */
	SINGLE_DATA_JSON 
}
