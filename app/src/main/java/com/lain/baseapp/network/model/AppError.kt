package com.lain.baseapp.network.model

/**
 * This class is the base error.
 */
sealed class AppError

/**
 * This class is the http error representation.
 */
data class HttpError(val code: Int, val body: String) : AppError()

/**
 * This class is network error representation.
 */
data class NetworkError(val throwable: Throwable) : AppError()

/**
 * This class is the unknown error representation.
 */
data class UnknownAppError(val throwable: Throwable) : AppError()

data class InternalAppError(val message: String) : AppError()