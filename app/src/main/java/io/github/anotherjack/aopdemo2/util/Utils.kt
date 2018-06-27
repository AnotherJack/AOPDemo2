package io.github.anotherjack.aopdemo2.util

import android.content.Context
import android.widget.Toast

/**
 * Created by jack on 2018/6/27.
 */

fun Context.toast(string: String){
    Toast.makeText(this,string,Toast.LENGTH_SHORT).show()
}