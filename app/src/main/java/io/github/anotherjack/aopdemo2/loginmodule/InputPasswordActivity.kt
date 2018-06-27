package io.github.anotherjack.aopdemo2.loginmodule

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import io.github.anotherjack.aopdemo2.R
import kotlinx.android.synthetic.main.activity_input_password.*

class InputPasswordActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_input_password)

        btn_success.setOnClickListener {
            LoginManager.isLogin = true
            val intent = Intent(this,InputAccountActivity::class.java)
            intent.putExtra("loginResult",true)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP)
            startActivity(intent)
        }

        btn_fail.setOnClickListener {
            LoginManager.isLogin = false
            val intent = Intent(this,InputAccountActivity::class.java)
            intent.putExtra("loginResult",false)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP)
            startActivity(intent)
        }
    }
}
