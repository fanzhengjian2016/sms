package com.shenjun.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @description 
 * @author shenjun
 * {@link sj_enginner@sina.com}
 * 版权个人所有，严禁侵权。
 */
@Retention(RetentionPolicy.RUNTIME)   
@Target({ElementType.TYPE, ElementType.METHOD}) 
public @interface WebServiceDescription {
	public String description();
}
