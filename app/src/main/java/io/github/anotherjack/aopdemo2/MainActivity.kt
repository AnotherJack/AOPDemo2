package io.github.anotherjack.aopdemo2

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn1.setOnClickListener {
            val intent = Intent(this,io.github.anotherjack.aopdemo2.solution1.UserListActivity::class.java)
            startActivity(intent)
        }

        btn2.setOnClickListener {
            val intent = Intent(this,io.github.anotherjack.aopdemo2.solution2.UserListActivity::class.java)
            startActivity(intent)
        }

        btn3.setOnClickListener {
            val intent = Intent(this,io.github.anotherjack.aopdemo2.solution3.UserListActivity::class.java)
            startActivity(intent)
        }
    }
}
