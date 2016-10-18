package com.easyorm.db.annotation;


import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import com.easyorm.db.enums.Strategy;

/**
 * 冲突策略
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface Conflict {
    public Strategy value();
}
