package io.github.anotherjack.aopdemo2.solution1

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import io.github.anotherjack.aopdemo2.R
import io.github.anotherjack.aopdemo2.adapter.UserListAdapter
import io.github.anotherjack.aopdemo2.bean.User
import kotlinx.android.synthetic.main.activity_user_list.*

class UserListActivity : AppCompatActivity() {
    private lateinit var mAdapter:UserListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_list)
        initRecyclerView()
        loadData()

    }

    private fun initRecyclerView(){
        recycler_view.layoutManager = LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)
        mAdapter = UserListAdapter(mutableListOf())
        mAdapter.bindToRecyclerView(recycler_view)
        mAdapter.setOnItemChildClickListener { adapter, view, position ->
            when(view.id){
                R.id.tv_like -> doLike(position)
                R.id.tv_comment -> doComment(position)
                R.id.tv_block -> doBlock(position)
            }
        }

    }

    private fun loadData() {
        //假数据
        for (i in 0..20){
            mAdapter.addData(User("User $i"))
        }
    }

    private fun doLike(position:Int) {

    }

    private fun doComment(position:Int){

    }

    private fun doBlock(position:Int){

    }
}
