package com.lain.baseapp.network.model

/**
 * This class is the base error.
 */
sealed class ApiError

/**
 * This class is the http error representation.
 */
data class HttpError(val code: Int, val body: String) : ApiError()

/**
 * This class is network error representation.
 */
data class NetworkError(val throwable: Throwable) : ApiError()

/**
 * This class is the unknown error representation.
 */
data class UnknownApiError(val throwable: Throwable) : ApiError()