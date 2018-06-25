package io.github.anotherjack.aopdemo2.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import io.github.anotherjack.aopdemo2.R
import io.github.anotherjack.aopdemo2.bean.User

/**
 * Created by jack on 2018/6/25.
 */
class UserListAdapter(data: MutableList<User>) : BaseQuickAdapter<User, BaseViewHolder>(R.layout.user_item,data) {
    override fun convert(helper: BaseViewHolder, item: User) {
            helper.setText(R.id.tv_username, item.name)
                    .addOnClickListener(R.id.tv_like)
                    .addOnClickListener(R.id.tv_comment)
                    .addOnClickListener(R.id.tv_block)
    }
}