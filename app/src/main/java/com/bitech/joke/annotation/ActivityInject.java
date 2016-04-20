package com.bitech.joke.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <p></p>
 * Created on 2016/4/5 13:39.
 *
 * @author Lucy
 */

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface ActivityInject {

    //获取contentView
    int contentViewId() default -1;

    //是否滑动关闭
    boolean isSlidr() default true;

    //标题
    String toolBarTitle() default "";

    //左边按钮图片
    int toolBarIndicator() default -1;
}
