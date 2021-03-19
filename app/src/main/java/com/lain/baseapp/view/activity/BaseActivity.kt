package com.lain.baseapp.view.activity

import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.airbnb.lottie.LottieAnimationView
import com.lain.baseapp.network.model.*
import com.lain.baseapp.network.model.`interface`.HandleErrors
import com.lain.baseapp.view.Router
import com.squareup.moshi.Moshi

/**
 * This class is the base activity, contains the base behavior for all activities.
 */
abstract class BaseActivity: AppCompatActivity() {

    /*==============================================================================================
    BASE METHODS
    ==============================================================================================*/
    /**
     * Show or hide a loader.
     * @param loader: the loader to hide / show.
     * @param loading: control the loader visibility, true show loading, false hide loading.
     */
    fun showLoader(loader: LottieAnimationView, loading: Boolean){
        with(loader){
            this.visibility = if(loading) View.VISIBLE else View.GONE
        }
    }

    /**
     * Handle the api errors.
     * @param error: the error to handle.
     * @param handleErrors: a interface to define the behavior for specific errors, if is null then send to error activity.
     */
    fun handleApiError(error: AppError, handleErrors: HandleErrors? = null){

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

        }else if(error is UnknownAppError){

            if(handleErrors != null){
                handleErrors.onNetworkError(throwable = error.throwable)
                return
            }


            Router.goToError(context = this, error = "Error: ${error.throwable.message}")

        } else {
            error as InternalAppError

            if(handleErrors != null){
                handleErrors.onInternalAppError(message = error.message)
                return
            }


            Router.goToError(context = this, error = "Error: ${error.message}")
        }
    }

}