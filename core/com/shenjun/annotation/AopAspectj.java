package com.shenjun.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 这一个类主要用于请求的方法是否需要进行验证类。
 * @author shenjun
 */
@Retention(RetentionPolicy.RUNTIME)   
@Target({ElementType.TYPE, ElementType.METHOD}) 
public @interface AopAspectj {
	boolean accessRead() default false;   
}  

