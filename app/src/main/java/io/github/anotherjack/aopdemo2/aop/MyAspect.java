package io.github.anotherjack.aopdemo2.aop;

import android.app.Activity;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

import io.github.anotherjack.aopdemo2.MyApplication;
import io.github.anotherjack.aopdemo2.annotation.RequireAuth;
import io.github.anotherjack.aopdemo2.annotation.RequireLogin;
import io.github.anotherjack.aopdemo2.annotation.RequireVip;
import io.github.anotherjack.aopdemo2.annotation.ShowConfirm;
import io.github.anotherjack.aopdemo2.authmodule.AuthManager;
import io.github.anotherjack.aopdemo2.loginmodule.LoginManager;
import io.github.anotherjack.aopdemo2.vipmodule.VipManager;

/**
 * Created by jack on 2018/6/28.
 */

@Aspect
public class MyAspect {
    //所有方法的execution
    @Pointcut("execution(* *..*.*(..))")
    public void anyExecution() {

    }

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
            Activity topActivity = MyApplication.Companion.getTopActivity();
            if (topActivity != null) {
                LoginManager.INSTANCE.toLogin(topActivity, new LoginManager.LoginCallback() {
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
            Activity topActivity = MyApplication.Companion.getTopActivity();
            if (topActivity != null) {
                AuthManager.INSTANCE.toAuth(topActivity, new AuthManager.AuthCallback() {
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


    //注解有RequireVip
    @Pointcut("@annotation(requireVip)")
    public void annotatedWithRequireVip(RequireVip requireVip) {

    }

    @Pointcut("anyExecution() && annotatedWithRequireVip(requireVip)")
    public void requireVipPointcut(RequireVip requireVip) {

    }

    @Around("requireVipPointcut(requireVip)")
    public void requireVip(final ProceedingJoinPoint proceedingJoinPoint, RequireVip requireVip) throws Throwable {
        final boolean proceed = requireVip.proceed();
        final int requireLevel = requireVip.requireLevel();

        if (VipManager.INSTANCE.getVipLevel() >= requireLevel) {
            proceedingJoinPoint.proceed();
        } else {
            Activity topActivity = MyApplication.Companion.getTopActivity();
            if (topActivity != null) {
                VipManager.INSTANCE.toBuyVip(topActivity, new VipManager.VipCallback() {
                    @Override
                    public void onBuyVip(int vipLevel) {
                        if(proceed && vipLevel>=requireLevel){
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


    //注解有showConfirm
    @Pointcut("@annotation(showConfirm)")
    public void annotatedWithShowConfirm(ShowConfirm showConfirm) {

    }

    @Pointcut("anyExecution() && annotatedWithShowConfirm(showConfirm)")
    public void showConfirmPointcut(ShowConfirm showConfirm) {

    }

    @Around("showConfirmPointcut(showConfirm)")
    public void showConfirm(final ProceedingJoinPoint proceedingJoinPoint, ShowConfirm showConfirm){
        int icon = showConfirm.icon();
        String title = showConfirm.title();
        String message = showConfirm.message();
        String positiveText = showConfirm.positiveText();
        String negativeText = showConfirm.negativeText();

        Activity topActivity = MyApplication.Companion.getTopActivity();
        if(topActivity!=null){
            new AlertDialog.Builder(topActivity)
                    .setIcon(icon)
                    .setTitle(title)
                    .setMessage(message)
                    .setPositiveButton(positiveText, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            try {
                                proceedingJoinPoint.proceed();
                            } catch (Throwable throwable) {
                                throwable.printStackTrace();
                            }
                        }
                    })
                    .setNegativeButton(negativeText, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    })
                    .create()
                    .show();
        }

    }

}
