package io.github.anotherjack.aopdemo2.authmodule

import android.app.Activity
import android.content.Intent
import io.github.anotherjack.aopdemo2.annotation.RequireLogin
import io.github.anotherjack.aopdemo2.util.avoidonresult.AvoidOnResult

/**
 * Created by jack on 2018/6/27.
 */
object AuthManager {
    var isAuthed = false

    @RequireLogin(proceed = true)
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