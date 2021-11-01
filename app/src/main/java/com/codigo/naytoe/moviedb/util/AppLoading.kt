package com.codigo.naytoe.moviedb.util

import android.app.Activity
import android.app.Dialog
import android.view.Window
import android.view.WindowManager
import com.codigo.naytoe.moviedb.R

class AppLoading(var activity: Activity) {

    private var dialog: Dialog? = null

    fun showDialog() {
        dialog = Dialog(activity)
        dialog!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog!!.setCancelable(false)
        dialog!!.setContentView(R.layout.custom_app_loading)
        dialog!!.show()
        dialog?.window?.clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE)
    }

    fun hideDialog() {
        dialog!!.dismiss()
    }

}