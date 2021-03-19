package com.lain.baseapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.lain.baseapp.view.Router

/**
 * This class contains the splash screen activity.
 */
class Splash: AppCompatActivity() {

    /*==============================================================================================
    ANDROID METHODS
    ==============================================================================================*/
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Router.goToMain(context = this)
        finish()
    }

}