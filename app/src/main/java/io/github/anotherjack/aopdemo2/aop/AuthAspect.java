package io.github.anotherjack.aopdemo2.aop;

import android.app.Activity;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

import io.github.anotherjack.aopdemo2.MyApplication;
import io.github.anotherjack.aopdemo2.annotation.RequireAuth;
import io.github.anotherjack.aopdemo2.authmodule.AuthManager;

/**
 * Created by jack on 2018/6/30.
 */
@Aspect
public class AuthAspect extends CommonPointcut {
    //注解有RequireAuth
    @Pointcut("@annotation(requireAuth)")
    public void annotatedWithRequireAuth(RequireAuth requireAuth) {

    }

    @Pointcut("anyExecution() && annotatedWithRequireAuth(requireAuth)")
    public void requireAuthPointcut(RequireAuth requireAuth) {

    }

    @Around("requireAuthPointcut(requireAuth)")
    public void requireAuth(final ProceedingJoinPoint proceedingJoinPoint, RequireAuth requireAuth) throws Throwable {
        final boolean proceed = requireAuth.proceed();

        if (AuthManager.INSTANCE.isAuthed()) {
            proceedingJoinPoint.proceed();
        } else {
//            Activity activity = MyApplication.Companion.getTopActivity();
            Activity activity = AspectUtils.getActivity(proceedingJoinPoint);
            if (activity != null) {
                AuthManager.INSTANCE.toAuth(activity, new AuthManager.AuthCallback() {
                    @Override
                    public void onAuthResult(boolean authResult) {
                        if (authResult && proceed) {
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
