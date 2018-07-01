package io.github.anotherjack.aopdemo2.aop;

import android.app.Activity;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

import io.github.anotherjack.aopdemo2.annotation.RequireVip;
import io.github.anotherjack.aopdemo2.vipmodule.VipManager;

/**
 * Created by jack on 2018/6/30.
 */
@Aspect
public class VipAspect extends CommonPointcut{

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
//            Activity activity = MyApplication.Companion.getTopActivity();
            Activity activity = AspectUtils.getActivity(proceedingJoinPoint);
            if (activity != null) {
                VipManager.INSTANCE.toBuyVip(activity, new VipManager.VipCallback() {
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
}
