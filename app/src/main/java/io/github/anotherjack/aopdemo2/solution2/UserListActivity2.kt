package io.github.anotherjack.aopdemo2.solution2

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import io.github.anotherjack.aopdemo2.R
import io.github.anotherjack.aopdemo2.adapter.UserListAdapter
import io.github.anotherjack.aopdemo2.authmodule.AuthManager
import io.github.anotherjack.aopdemo2.bean.User
import io.github.anotherjack.aopdemo2.loginmodule.LoginManager
import io.github.anotherjack.aopdemo2.util.toast
import io.github.anotherjack.aopdemo2.vipmodule.VipManager
import kotlinx.android.synthetic.main.activity_user_list.*

class UserListActivity2 : AppCompatActivity() {
    private lateinit var mAdapter: UserListAdapter

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
                R.id.tv_like -> {
                    if (LoginManager.isLogin) {
                        doLike(position)
                    } else {
                        LoginManager.toLogin(this, object : LoginManager.LoginCallback {
                            override fun onLoginResult(loginResult: Boolean) {
                                if (loginResult) {
                                    doLike(position)
                                }
                            }

                        })
                    }
                }

                R.id.tv_comment -> {
                    if(AuthManager.isAuthed){
                        doComment(position)
                    }else{
                        AuthManager.toAuth2(this,object :AuthManager.AuthCallback{
                            override fun onAuthResult(authResult: Boolean) {
                                if(authResult){
                                    doComment(position)
                                }
                            }
                        })
                    }
                }

                R.id.tv_block -> {
                    if (VipManager.vipLevel >= 3) {
                        doBlock(position)
                    } else {
                        VipManager.toBuyVip(this, object : VipManager.VipCallback {
                            override fun onBuyVip(vipLevel: Int) {
                                if (vipLevel >= 3) {
                                    doBlock(position)
                                }
                            }

                        })
                    }
                }
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
        val user = mAdapter.getItem(position)
        toast("点赞了 ${user?.name}")
    }

    //需要登录且通过实名认证才能评论
    private fun doComment(position: Int) {
        val user = mAdapter.getItem(position)
        toast("评论了 ${user?.name}")
    }

    //需要达到vip3才能屏蔽其他人
    private fun doBlock(position: Int) {
        val user = mAdapter.getItem(position)
        toast("屏蔽了 ${user?.name}")
    }
}