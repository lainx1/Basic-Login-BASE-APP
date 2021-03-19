package com.lain.baseapp.view.activity

import android.os.Bundle
import com.lain.baseapp.R
import com.lain.baseapp.view.Router
import com.lain.baseapp.viewmodel.BaseViewModel
import com.lain.baseapp.viewmodel.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

/**
 * This class is the main activity.
 */
@AndroidEntryPoint
class MainActivity : BaseActivity() {

    /**
     * The base view model.
     */
    @Inject
    lateinit var baseViewModel: BaseViewModel


    /*==============================================================================================
    ANDROID METHODS
    ==============================================================================================*/

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        /**
         * Validate that are 1 user login
         */
        val user = intent.getStringExtra(Router.Extras.USER.name)
        if(user == null) {
            Router.goToLogin(context = this)
            finish()
        }

        userTV.text = user

    }

    override fun onStart() {

        super.onStart()
        logoutBTN.setOnClickListener {
            Router.goToLogin(context = this)
            finish()
        }
    }
}