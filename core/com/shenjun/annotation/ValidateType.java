package com.shenjun.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * String ValidateChar(); //验证代码
 * String ValidateMake(); //验证描述
 * @author: shenjun
 * {@link shenjunhappy@live.com }
 * 版权所有,严禁侵权.
 */
@Retention(RetentionPolicy.RUNTIME) 
@Target(ElementType.METHOD)
public @interface ValidateType {
	/**
	 * 
	 * @return 验证代码
	 */
	String ValidateChar();
	/**
	 * 
	 * @return 验证参数
	 */
	String [] args();
}
