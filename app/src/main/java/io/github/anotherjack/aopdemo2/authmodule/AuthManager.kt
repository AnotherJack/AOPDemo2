package io.github.anotherjack.aopdemo2.authmodule

import android.app.Activity
import android.content.Intent
import io.github.anotherjack.aopdemo2.annotation.RequireLogin
import io.github.anotherjack.aopdemo2.loginmodule.LoginManager
import io.github.anotherjack.aopdemo2.util.avoidonresult.AvoidOnResult

/**
 * Created by jack on 2018/6/27.
 */
object AuthManager {
    var isAuthed = false

    //方式2
    fun toAuth2(activity: Activity, authCallback: AuthCallback){
        if(LoginManager.isLogin){
            realToAuth(activity,authCallback)
        }else{
            LoginManager.toLogin(activity,object :LoginManager.LoginCallback{
                override fun onLoginResult(loginResult: Boolean) {
                    if(loginResult){
                        realToAuth(activity,authCallback)
                    }else{
                        authCallback.onAuthResult(false)
                    }
                }

            })
        }
    }
    private fun realToAuth(activity: Activity, authCallback: AuthCallback){
        AvoidOnResult(activity).startForResult(AuthActivity::class.java,object :AvoidOnResult.Callback{
            override fun onActivityResult(resultCode: Int, data: Intent?) {
                if(resultCode == Activity.RESULT_OK && data?.getBooleanExtra("authResult",false) == true){
                    authCallback.onAuthResult(true)
                }else{
                    authCallback.onAuthResult(false)
                }
            }
        })
    }



    //方式3请添加注解，方式四请注掉下面的注解
//    @RequireLogin(proceed = true)
    fun toAuth(activity: Activity, authCallback: AuthCallback){
        AvoidOnResult(activity).startForResult(AuthActivity::class.java,object :AvoidOnResult.Callback{
            override fun onActivityResult(resultCode: Int, data: Intent?) {
                if(resultCode == Activity.RESULT_OK && data?.getBooleanExtra("authResult",false) == true){
                    authCallback.onAuthResult(true)
                }else{
                    authCallback.onAuthResult(false)
                }
            }
        })
    }

    interface AuthCallback{
        fun onAuthResult(authResult:Boolean)
    }
}