package com.shenjun.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import com.shenjun.enums.ReturnEnum;
/**
 * action类中，进行类的注解或者方法的注解，用于确定返回的类型是什么<ReturnEnum>？
 * @author: 沈军
 * {@link shenjunhappy@live.com }
 * 版权所有,严禁侵权.
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@ReturnType(ReturnEnum.JSP_CODE_NAME_TYPE)
public @interface ReturnType {
	ReturnEnum value(); //验证代码
}
