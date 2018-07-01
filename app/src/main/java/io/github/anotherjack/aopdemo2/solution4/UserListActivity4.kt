package io.github.anotherjack.aopdemo2.solution4

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import io.github.anotherjack.aopdemo2.R
import io.github.anotherjack.aopdemo2.adapter.UserListAdapter
import io.github.anotherjack.aopdemo2.annotation.RequireAuth
import io.github.anotherjack.aopdemo2.annotation.RequireLogin
import io.github.anotherjack.aopdemo2.annotation.RequireVip
import io.github.anotherjack.aopdemo2.authmodule.AuthManager
import io.github.anotherjack.aopdemo2.bean.User
import io.github.anotherjack.aopdemo2.loginmodule.LoginManager
import io.github.anotherjack.aopdemo2.util.toast
import io.github.anotherjack.aopdemo2.vipmodule.VipManager
import kotlinx.android.synthetic.main.activity_user_list.*

class UserListActivity4 : AppCompatActivity() {
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
    @RequireLogin(proceed = true)
    private fun doLike(position: Int) {
        val user = mAdapter.getItem(position)
        toast("点赞了 ${user?.name}")
    }

    //需要登录且通过实名认证才能评论
    @RequireLogin(proceed = true)
    @RequireAuth(proceed = true)
    private fun doComment(position: Int) {
        val user = mAdapter.getItem(position)
        toast("评论了 ${user?.name}")
    }

    //需要达到vip3且通过实名认证才能屏蔽其他人
    @RequireVip(proceed = true,requireLevel = 3)
    @RequireAuth(proceed = true)
    private fun doBlock(position: Int) {
        val user = mAdapter.getItem(position)
        toast("屏蔽了 ${user?.name}")
    }
}