package com.ccand99.bluetoothx_demo.kthighfunction

import android.content.Context
import android.widget.Toast

fun String.showToast(context: Context,duration: Int = Toast.LENGTH_SHORT){
    Toast.makeText(context,this,duration).show()
}

fun Int.showToast(context: Context,duration: Int = Toast.LENGTH_SHORT){
    Toast.makeText(context,this,duration).show()
}