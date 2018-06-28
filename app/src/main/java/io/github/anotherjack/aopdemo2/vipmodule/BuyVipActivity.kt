package io.github.anotherjack.aopdemo2.vipmodule

import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import io.github.anotherjack.aopdemo2.R
import io.github.anotherjack.aopdemo2.annotation.RequireLogin
import io.github.anotherjack.aopdemo2.loginmodule.InputAccountActivity
import io.github.anotherjack.aopdemo2.loginmodule.LoginManager
import kotlinx.android.synthetic.main.activity_buy_vip.*

class BuyVipActivity : AppCompatActivity() {
    private val REQUEST_CODE_LOGIN = 10

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_buy_vip)

        btn_buy.setOnClickListener {
            buyVip()
        }
    }

    @RequireLogin(proceed = false)
    private fun buyVip(){
//        if(!LoginManager.isLogin){
//            val i = Intent(this, InputAccountActivity::class.java)
//            startActivityForResult(i, REQUEST_CODE_LOGIN)
//            return
//        }

        val level = et_level.text.toString().toInt()
        VipManager.vipLevel = level

        val i = Intent()
        i.putExtra("vipLevel",level)
        setResult(Activity.RESULT_OK,i)
        finish()
    }

}
