package io.github.anotherjack.aopdemo2

import android.app.Activity
import android.app.Application
import android.os.Bundle
import io.github.anotherjack.aopdemo2.util.Weak

/**
 * Created by jack on 2018/6/28.
 */
class MyApplication: Application() {
    companion object {
        var topActivity by Weak<Activity>()
    }

    override fun onCreate() {
        super.onCreate()

        registerActivityLifecycleCallbacks(object :ActivityLifecycleCallbacks{
            override fun onActivityPaused(activity: Activity?) {

            }

            override fun onActivityResumed(activity: Activity?) {
                topActivity = activity
            }

            override fun onActivityStarted(activity: Activity?) {

            }

            override fun onActivityDestroyed(activity: Activity?) {

            }

            override fun onActivitySaveInstanceState(activity: Activity?, outState: Bundle?) {

            }

            override fun onActivityStopped(activity: Activity?) {

            }

            override fun onActivityCreated(activity: Activity?, savedInstanceState: Bundle?) {

            }

        })
    }
}