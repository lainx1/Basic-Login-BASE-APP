package com.lain.baseapp.view

import android.content.Context
import android.content.Intent
import com.lain.baseapp.view.activity.ErrorActivity
import com.lain.baseapp.view.activity.MainActivity

object Router {
    enum class Extras(extra: String) {
        ERROR("error")
    }

    fun goToMain(context: Context){
        val intent = Intent(context, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
        context.startActivity(intent)
    }

    fun goToError(context: Context, error: String? = null){
        val intent = Intent(context, ErrorActivity::class.java)
        intent.putExtra(Extras.ERROR.name, error ?: "")
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
        context.startActivity(intent)
    }
}