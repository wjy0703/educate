/**
 * Copyright (c) 2005-2009 springside.org.cn
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * 
 * $Id: Groups.java 1141 2010-07-31 17:54:22Z calvinxiu $
 */
package cn.com.educate.core.test.groups;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 实现TestNG Groups分组执行用例功能的annotation.
 * 
 * @author freeman
 * @author calvin
 */
@Retention(RetentionPolicy.RUNTIME)
@Target( { ElementType.METHOD, ElementType.TYPE })
@Documented
public @interface Groups {
	/**
	 * 执行所有组别的测试.
	 */
	final String ALL = "all";

	/**
	 * 组别定义,默认为ALL.
	 */
	String value() default ALL;
}
