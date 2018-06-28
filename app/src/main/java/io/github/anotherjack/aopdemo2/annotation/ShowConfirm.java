package io.github.anotherjack.aopdemo2.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import io.github.anotherjack.aopdemo2.R;

/**
 * Created by jack on 2018/6/28.
 */

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ShowConfirm {
    int icon() default R.mipmap.ic_launcher;
    String title() default "默认标题";
    String message() default "默认message";
    String positiveText() default "确认";
    String negativeText() default "取消";
}
