package com.easyutils.db.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Column Name..
 * 为属性命名“列名”，如果没有设置，将以属性名字命名它在表中的“列名”；
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Column {
	/**
	 * Table Name
	 */
	public String value();
}
