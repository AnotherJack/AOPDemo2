package io.github.anotherjack.aopdemo2.solution1

import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import io.github.anotherjack.aopdemo2.R
import io.github.anotherjack.aopdemo2.adapter.UserListAdapter
import io.github.anotherjack.aopdemo2.authmodule.AuthActivity
import io.github.anotherjack.aopdemo2.authmodule.AuthManager
import io.github.anotherjack.aopdemo2.bean.User
import io.github.anotherjack.aopdemo2.loginmodule.InputAccountActivity
import io.github.anotherjack.aopdemo2.loginmodule.LoginManager
import io.github.anotherjack.aopdemo2.util.toast
import io.github.anotherjack.aopdemo2.vipmodule.BuyVipActivity
import io.github.anotherjack.aopdemo2.vipmodule.VipManager
import kotlinx.android.synthetic.main.activity_user_list.*

class UserListActivity1 : AppCompatActivity() {
    private lateinit var mAdapter: UserListAdapter
    private val REQUEST_CODE_LOGIN = 23
    private val REQUEST_CODE_AUTH = 24
    private val REQUEST_CODE_VIP = 25
    var action = ""
    var clickedPosition = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_list)
        reset_login.setOnClickListener { LoginManager.isLogin = false }
        reset_auth.setOnClickListener { AuthManager.isAuthed = false }
        reset_vip.setOnClickListener { VipManager.vipLevel = 0 }
        initRecyclerView()
        loadData()

    }

    private fun initRecyclerView() {
        recycler_view.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        mAdapter = UserListAdapter(mutableListOf())
        mAdapter.bindToRecyclerView(recycler_view)
        mAdapter.setOnItemChildClickListener { adapter, view, position ->
            when (view.id) {
                R.id.tv_like -> doLike(position)
                R.id.tv_comment -> doComment(position)
                R.id.tv_block -> doBlock(position)
            }
        }

    }

    private fun loadData() {
        //假数据
        for (i in 0..20) {
            mAdapter.addData(User("User $i"))
        }
    }

    //需要登录才能点赞
    private fun doLike(position: Int) {
        if (!LoginManager.isLogin) {
            action = "like"
            clickedPosition = position
            val i = Intent(this, InputAccountActivity::class.java)
            startActivityForResult(i, REQUEST_CODE_LOGIN)
            return
        }


        val user = mAdapter.getItem(position)
        toast("点赞了 ${user?.name}")

    }

    //需要登录且通过实名认证才能评论
    private fun doComment(position: Int) {
        if (!LoginManager.isLogin) {
            action = "comment"
            clickedPosition = position
            val i = Intent(this, InputAccountActivity::class.java)
            startActivityForResult(i, REQUEST_CODE_LOGIN)
            return
        }

        if (!AuthManager.isAuthed) {
            action = "comment"
            clickedPosition = position
            val i = Intent(this, AuthActivity::class.java)
            startActivityForResult(i, REQUEST_CODE_AUTH)
            return
        }

        val user = mAdapter.getItem(position)
        toast("评论了 ${user?.name}")
    }

    //需要达到vip3才能屏蔽其他人
    private fun doBlock(position: Int) {
        if(VipManager.vipLevel<3){
            action = "block"
            clickedPosition = position
            val i = Intent(this,BuyVipActivity::class.java)
            startActivityForResult(i,REQUEST_CODE_VIP)
            return
        }


        val user = mAdapter.getItem(position)
        toast("屏蔽了 ${user?.name}")
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == REQUEST_CODE_LOGIN) {
                val loginResult = data?.getBooleanExtra("loginResult", false)
                if (loginResult == true) {
                    when (action) {
                        "like" -> doLike(clickedPosition)
                        "comment" -> doComment(clickedPosition)
                        "block" -> doBlock(clickedPosition)
                    }
                }
            } else if (requestCode == REQUEST_CODE_AUTH) {
                val authResult = data?.getBooleanExtra("authResult",false)
                if(authResult == true){
                    when(action){
                        "comment" -> doComment(clickedPosition)
                    }
                }

            } else if (requestCode == REQUEST_CODE_VIP) {
                val vipLevel:Int = data?.getIntExtra("vipLevel",0) ?:0
                if(vipLevel>=3){
                    when(action){
                        "block" -> doBlock(clickedPosition)
                    }
                }
            }
        }
    }
}
