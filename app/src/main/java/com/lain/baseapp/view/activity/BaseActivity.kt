package com.lain.baseapp.view.activity

import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.airbnb.lottie.LottieAnimationView
import com.lain.baseapp.network.model.*
import com.lain.baseapp.network.model.`interface`.HandleErrors
import com.lain.baseapp.view.Router
import com.squareup.moshi.Moshi

abstract class BaseActivity: AppCompatActivity() {

    fun showLoader(loader: LottieAnimationView, loading: Boolean){
        with(loader){
            this.visibility = if(loading) View.VISIBLE else View.GONE
        }
    }

    fun handleApiError(error: ApiError, handleErrors: HandleErrors? = null){

        if(error is HttpError){

            val httpErrorResponseAdapter = Moshi.Builder().build().adapter(HttpErrorResponse::class.java)
            val httpErrorResponse = httpErrorResponseAdapter.fromJson(error.body)

            if(handleErrors != null){
                handleErrors.onHttpError(httpErrorResponse = httpErrorResponse!!)
                return
            }

            Router.goToError(context = this, error = "Error: ${httpErrorResponse?.status}: ${httpErrorResponse?.message}")


        }else if(error is NetworkError){

            if(handleErrors != null){
                handleErrors.onNetworkError(throwable = error.throwable)
                return
            }

            //This is the default behavior
            Router.goToError(context = this, error = "Error: ${error.throwable.message}")

        }else{

            error as UnknownApiError

            if(handleErrors != null){
                handleErrors.onNetworkError(throwable = error.throwable)
                return
            }


            Router.goToError(context = this, error = "Error: ${error.throwable.message}")

        }
    }

}