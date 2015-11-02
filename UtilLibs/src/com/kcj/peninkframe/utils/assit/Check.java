package com.kcj.peninkframe.utils.assit;

import java.util.Collection;
import java.util.Map;

/**
 * @ClassName: Check
 * @Description: 检测类， 检测各种对象是否为null或empty
 * @author:
 * @date: 
 */
public class Check {

	public static boolean isEmpty(CharSequence str) {
		return isNull(str) || str.length() == 0;
	}

	public static boolean isEmpty(Object[] os) {
		return isNull(os) || os.length == 0;
	}

	public static boolean isEmpty(Collection<?> l) {
		return isNull(l) || l.isEmpty();
	}

	public static boolean isEmpty(Map<?, ?> m) {
		return isNull(m) || m.isEmpty();
	}

	public static boolean isNull(Object o) {
		return o == null;
	}
}
