package io.github.anotherjack.aopdemo2.vipmodule

import android.app.Activity
import android.content.Intent
import io.github.anotherjack.aopdemo2.util.avoidonresult.AvoidOnResult

/**
 * Created by jack on 2018/6/25.
 */
object VipManager {
    var vipLevel = 0

    fun toBuyVip(activity:Activity,vipCallback: VipCallback){
        AvoidOnResult(activity).startForResult(BuyVipActivity::class.java,object :AvoidOnResult.Callback{
            override fun onActivityResult(resultCode: Int, data: Intent?) {
                if(resultCode == Activity.RESULT_OK){
                    val vipLevel:Int = data?.getIntExtra("vipLevel",0) ?:0
                    vipCallback.onBuyVip(vipLevel)
                }
            }

        })
    }

    interface VipCallback{
        fun onBuyVip(vipLevel:Int)
    }
}