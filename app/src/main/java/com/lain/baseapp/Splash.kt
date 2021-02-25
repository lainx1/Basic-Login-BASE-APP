package com.lain.baseapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.lain.baseapp.view.Router

class Splash: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Router.goToMain(context = this)
    }

}