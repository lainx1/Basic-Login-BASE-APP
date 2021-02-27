package com.lain.baseapp.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.lain.baseapp.R
import com.lain.baseapp.view.Router
import kotlinx.android.synthetic.main.activity_error.*

/**
 * This class is the default error activity.
 */
class ErrorActivity : AppCompatActivity() {

    /*==============================================================================================
    ANDROID METHODS
    ==============================================================================================*/
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_error)

        //Set error
        errorTV.text = intent.getStringExtra(Router.Extras.ERROR.name)
    }

    override fun onStart() {
        super.onStart()
        reconnectBTN.setOnClickListener {
            Router.goToMain(context =  this@ErrorActivity)
        }
    }
}