package com.lkp.java.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by liukepeng on 2018/8/15.
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface NoInheritedTest {
    String value();
}
