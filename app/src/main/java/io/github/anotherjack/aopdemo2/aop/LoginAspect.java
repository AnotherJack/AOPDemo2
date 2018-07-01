package io.github.anotherjack.aopdemo2.aop;

import android.app.Activity;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

import io.github.anotherjack.aopdemo2.MyApplication;
import io.github.anotherjack.aopdemo2.annotation.RequireLogin;
import io.github.anotherjack.aopdemo2.loginmodule.LoginManager;

/**
 * Created by jack on 2018/6/30.
 */
@Aspect
public class LoginAspect extends CommonPointcut {
    //注解有RequireLogin
    @Pointcut("@annotation(requireLogin)")
    public void annotatedWithRequireLogin(RequireLogin requireLogin) {

    }

    @Pointcut("anyExecution() && annotatedWithRequireLogin(requireLogin)")
    public void requireLoginPointcut(RequireLogin requireLogin) {

    }

    @Around("requireLoginPointcut(requireLogin)")
    public void requireLogin(final ProceedingJoinPoint proceedingJoinPoint, RequireLogin requireLogin) throws Throwable {
        final boolean proceed = requireLogin.proceed();

        if (LoginManager.INSTANCE.isLogin()) {
            proceedingJoinPoint.proceed();
        } else {
//            Activity activity = MyApplication.Companion.getTopActivity();
            Activity activity = AspectUtils.getActivity(proceedingJoinPoint);
            if (activity != null) {
                LoginManager.INSTANCE.toLogin(activity, new LoginManager.LoginCallback() {
                    @Override
                    public void onLoginResult(boolean loginResult) {
                        if (loginResult && proceed) {
                            try {
                                proceedingJoinPoint.proceed();
                            } catch (Throwable throwable) {
                                throwable.printStackTrace();
                            }
                        }
                    }
                });
            }
        }
    }
}
