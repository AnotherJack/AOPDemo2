package io.github.anotherjack.aopdemo2.authmodule

import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import io.github.anotherjack.aopdemo2.R
import kotlinx.android.synthetic.main.activity_auth.*

class AuthActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)
        auth_success.setOnClickListener {
            AuthManager.isAuthed = true
            val i = Intent()
            i.putExtra("authResult",true)
            setResult(Activity.RESULT_OK,i)
            finish()
        }

        auth_fail.setOnClickListener {
            AuthManager.isAuthed = false
            val i = Intent()
            i.putExtra("authResult",false)
            setResult(Activity.RESULT_OK,i)
            finish()
        }
    }
}
