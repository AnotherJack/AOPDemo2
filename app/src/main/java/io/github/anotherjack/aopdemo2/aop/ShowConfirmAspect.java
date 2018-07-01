package io.github.anotherjack.aopdemo2.aop;

import android.app.Activity;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

import io.github.anotherjack.aopdemo2.MyApplication;
import io.github.anotherjack.aopdemo2.annotation.ShowConfirm;

/**
 * Created by jack on 2018/6/30.
 */
@Aspect
public class ShowConfirmAspect extends CommonPointcut {
    //注解有showConfirm
    @Pointcut("@annotation(showConfirm)")
    public void annotatedWithShowConfirm(ShowConfirm showConfirm) {

    }

    @Pointcut("anyExecution() && annotatedWithShowConfirm(showConfirm)")
    public void showConfirmPointcut(ShowConfirm showConfirm) {

    }

    @Around("showConfirmPointcut(showConfirm)")
    public void showConfirm(final ProceedingJoinPoint proceedingJoinPoint, ShowConfirm showConfirm) {
        int icon = showConfirm.icon();
        String title = showConfirm.title();
        String message = showConfirm.message();
        String positiveText = showConfirm.positiveText();
        String negativeText = showConfirm.negativeText();

//        Activity activity = MyApplication.Companion.getTopActivity();
        Activity activity = AspectUtils.getActivity(proceedingJoinPoint);
        if (activity != null) {
            new AlertDialog.Builder(activity)
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
