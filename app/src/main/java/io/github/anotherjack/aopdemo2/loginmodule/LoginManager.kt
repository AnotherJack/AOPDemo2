package io.github.anotherjack.aopdemo2.loginmodule

import android.app.Activity
import android.content.Intent
import io.github.anotherjack.aopdemo2.util.avoidonresult.AvoidOnResult

/**
 * Created by jack on 2018/6/25.
 */
object LoginManager {
    var isLogin = false

    fun toLogin(activity:Activity,loginCallback: LoginCallback) {
        AvoidOnResult(activity).startForResult(InputAccountActivity::class.java,object :AvoidOnResult.Callback{
            override fun onActivityResult(resultCode: Int, data: Intent?) {
                if(resultCode == Activity.RESULT_OK && data?.getBooleanExtra("loginResult",false)==true){
                    loginCallback.onLoginResult(true)
                }else{
                    loginCallback.onLoginResult(false)
                }
            }

        })
    }

    interface LoginCallback{
        fun onLoginResult(loginResult: Boolean)
    }
}