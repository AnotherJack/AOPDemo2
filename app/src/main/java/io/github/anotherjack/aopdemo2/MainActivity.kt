package io.github.anotherjack.aopdemo2

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import io.github.anotherjack.aopdemo2.annotation.ShowConfirm
import io.github.anotherjack.aopdemo2.solution4.UserListActivity4
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn1.setOnClickListener {
            val intent = Intent(this, io.github.anotherjack.aopdemo2.solution1.UserListActivity1::class.java)
            startActivity(intent)
        }

        btn2.setOnClickListener {
            val intent = Intent(this, io.github.anotherjack.aopdemo2.solution2.UserListActivity2::class.java)
            startActivity(intent)
        }

        btn3.setOnClickListener {
            val intent = Intent(this, io.github.anotherjack.aopdemo2.solution3.UserListActivity3::class.java)
            startActivity(intent)
        }

        btn4.setOnClickListener {
            val intent = Intent(this, UserListActivity4::class.java)
            startActivity(intent)
        }
    }

    @ShowConfirm(title = "提示", message = "确认退出应用？", positiveText = "确定", negativeText = "取消")
    override fun onBackPressed() {
        super.onBackPressed()
    }
}
