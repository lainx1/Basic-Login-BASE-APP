package com.lain.baseapp.view.activity

import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import com.lain.baseapp.R
import com.lain.baseapp.network.model.HttpErrorResponse
import com.lain.baseapp.network.model.`interface`.HandleErrors
import com.lain.baseapp.view.Router
import com.lain.baseapp.viewmodel.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.loader.*
import javax.inject.Inject

@AndroidEntryPoint
class LoginActivity : BaseActivity() {

    @Inject
    lateinit var loginViewModel: LoginViewModel

    /*==============================================================================================
    ANDROID METHODS
    ==============================================================================================*/
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        /**
         * Observe loading
         */
        loginViewModel!!.loading.observe(this, {
            showLoader(loader = loader, loading =  it)
        })

        /**
         * Observe user that login
         */
        loginViewModel.user.observe(this, {
                Router.goToMain(context = this, email = it)
                finish()
        })

        /**
         * Observe errors
         */
        loginViewModel.error.observe(this, {
            handleApiError(error = it, object : HandleErrors{
                override fun onHttpError(httpErrorResponse: HttpErrorResponse) {
                    Router.goToError(context = this@LoginActivity, error = httpErrorResponse.message)
                }

                override fun onNetworkError(throwable: Throwable) {
                    Router.goToError(context = this@LoginActivity, error = throwable.message)
                }

                override fun unknownApiError(throwable: Throwable) {
                    Router.goToError(context = this@LoginActivity, error = throwable.message)
                }

                override fun onInternalAppError(message: String) {
                    Snackbar.make(mainCL, message, Snackbar.LENGTH_SHORT).show()
                }

            })
        })
    }

    override fun onStart() {
        super.onStart()

        loginBTN.setOnClickListener {
           loginViewModel.login(
               email = emailET.text.toString(),
               password = passwordET.text.toString()
           )
        }


    }
}