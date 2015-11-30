package com.liteutil.android.http.annotation;

import com.liteutil.android.http.request.param.CacheMode;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author MaTianyu
 * @date 2015-04-26
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface HttpCacheMode {
    CacheMode value();
}
