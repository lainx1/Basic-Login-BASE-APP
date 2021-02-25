package com.lain.baseapp.network.model.`interface`

import com.lain.baseapp.network.model.HttpErrorResponse

interface HandleErrors {

    fun onHttpError(httpErrorResponse: HttpErrorResponse)

    fun onNetworkError(throwable: Throwable)

    fun unknownApiError(throwable: Throwable)
}