package io.github.anotherjack.aopdemo2.loginmodule

import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import io.github.anotherjack.aopdemo2.R
import kotlinx.android.synthetic.main.activity_input_account.*

class InputAccountActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_input_account)

        btn_next.setOnClickListener {
            val intent = Intent(this,InputPasswordActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        val loginResult = intent?.getBooleanExtra("loginResult",false)
        val i = Intent()
        i.putExtra("loginResult",loginResult)
        setResult(Activity.RESULT_OK,i)
        finish()
    }
}
