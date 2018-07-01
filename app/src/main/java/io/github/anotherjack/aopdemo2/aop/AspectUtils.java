package io.github.anotherjack.aopdemo2.aop;

import android.app.Activity;

import org.aspectj.lang.JoinPoint;

import io.github.anotherjack.aopdemo2.MyApplication;

/**
 * Created by jack on 2018/6/30.
 */
public class AspectUtils {
    public static Activity getActivity(JoinPoint joinPoint){
        //先看this
        if(joinPoint.getThis() instanceof Activity){
            return (Activity) joinPoint.getThis();
        }

        //target
        if(joinPoint.getTarget() instanceof Activity){
            return (Activity) joinPoint.getTarget();
        }

        //args
        for(Object arg:joinPoint.getArgs()){
            if (arg instanceof Activity){
                return (Activity) arg;
            }
        }

        //如果实在找不到，再返回topActivity
        return MyApplication.Companion.getTopActivity();
    }
}
