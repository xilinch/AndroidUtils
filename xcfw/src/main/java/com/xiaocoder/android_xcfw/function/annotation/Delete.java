package com.xiaocoder.android_xcfw.function.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author xiaocoder
 * @email fengjingyu@foxmail.com
 * @description
 */
@Retention(RetentionPolicy.SOURCE)
@Target(ElementType.TYPE)
@Documented
public @interface Delete {

    String methodName();

    String where();

}
