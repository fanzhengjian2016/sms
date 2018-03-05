package com.shenjun.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import com.shenjun.enums.ReturnEnum;
/**
 * 这一类主要用于进行验证代码的情况。
 * @author shenjun
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@ReturnType(ReturnEnum.JSP_CODE_NAME_TYPE)
public @interface FilterAnnotation {
	/**
	 * 验证代码 Spring配置
	 * @return 返回验证的字符串
	 */
	String value();
}
