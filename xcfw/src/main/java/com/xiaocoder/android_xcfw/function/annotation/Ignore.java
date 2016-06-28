package com.xiaocoder.android_xcfw.function.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author xiaocoder
 * @email fengjingyu@foxmail.com
 * @description 一个标记，model里的该字段不存入数据库，配合db工具使用的，只能用于model的字段之上
 */
@Retention(RetentionPolicy.SOURCE)
@Target(ElementType.FIELD)
@Documented
public @interface Ignore {
}
