package com.lain.baseapp.view

import android.content.Context
import android.content.Intent
import com.lain.baseapp.view.activity.ErrorActivity
import com.lain.baseapp.view.activity.MainActivity

/**
 * This object is the router to all activities in the app.
 */
object Router {

    /**
     * The extras passed by activities.
     */
    enum class Extras(extra: String) {
        ERROR("error")
    }

    /**
     * Go to main activity.
     * @param context: the current context.
     */
    fun goToMain(context: Context){
        val intent = Intent(context, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
        context.startActivity(intent)
    }

    /**
     * Go to error activity.
     * @param context: the current context.
     */
    fun goToError(context: Context, error: String? = null){
        val intent = Intent(context, ErrorActivity::class.java)
        intent.putExtra(Extras.ERROR.name, error ?: "")
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
        context.startActivity(intent)
    }
}