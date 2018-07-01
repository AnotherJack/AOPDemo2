package io.github.anotherjack.aopdemo2.aop;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.DeclarePrecedence;

/**
 * Created by jack on 2018/6/30.
 */

@Aspect
@DeclarePrecedence("LoginAspect,VipAspect,AuthAspect")
public class CoordinationAspect {
    // empty
}
