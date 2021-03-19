package com.lain.baseapp.network.converters

import arrow.core.Either
import arrow.core.Left
import arrow.core.Right

import com.lain.baseapp.network.model.AppError
import com.lain.baseapp.network.model.HttpError
import com.lain.baseapp.network.model.NetworkError
import com.lain.baseapp.network.model.UnknownAppError

import okhttp3.Request
import okio.Timeout
import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import java.io.IOException
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

/**
 * This class convert the retrofit default response to an either response.
 */
internal class EitherCallAdapterFactory : CallAdapter.Factory() {

    override fun get(
        returnType: Type,
        annotations: Array<Annotation>,
        retrofit: Retrofit
    ): CallAdapter<*, *>? {
        if (getRawType(returnType) != Call::class.java) return null
        check(returnType is ParameterizedType) { "Return type must be a parameterized type." }

        val responseType = getParameterUpperBound(0, returnType)
        if (getRawType(responseType) != Either::class.java) return null
        check(responseType is ParameterizedType) { "Response type must be a parameterized type." }

        val leftType = getParameterUpperBound(0, responseType)
        if (getRawType(leftType) != AppError::class.java) return null

        val rightType = getParameterUpperBound(1, responseType)
        return EitherCallAdapter<Any>(rightType)
    }

}

private class EitherCallAdapter<R>(
    private val successType: Type
) : CallAdapter<R, Call<Either<AppError, R>>> {

    override fun adapt(call: Call<R>): Call<Either<AppError, R>> = EitherCall(call, successType)

    override fun responseType(): Type = successType

}

private class EitherCall<R>(
    private val delegate: Call<R>,
    private val successType: Type
) : Call<Either<AppError, R>> {

    override fun enqueue(callback: Callback<Either<AppError, R>>) = delegate.enqueue(
        object : Callback<R> {

            override fun onResponse(call: Call<R>, response: Response<R>) {
                callback.onResponse(this@EitherCall, Response.success(response.toEither()))
            }

            private fun Response<R>.toEither(): Either<AppError, R> {
                // Http error response (4xx - 5xx)
                if (!isSuccessful) {
                    val errorBody = errorBody()?.string() ?: ""
                    return Left(HttpError(code(), errorBody))
                }

                // Http success response with body
                body()?.let { body -> return Right(body) }

                // if we defined Unit as success type it means we expected no response body
                // e.g. in case of 204 No Content
                return if (successType == Unit::class.java) {
                    @Suppress("UNCHECKED_CAST")
                    Right(Unit) as Either<AppError, R>
                } else {
                    @Suppress("UNCHECKED_CAST")
                    Left(UnknownError("Response body was null")) as Either<AppError, R>
                }
            }

            override fun onFailure(call: Call<R>, throwable: Throwable) {
                val error = when (throwable) {
                    is IOException -> NetworkError(throwable)
                    else -> UnknownAppError(throwable)
                }
                callback.onResponse(this@EitherCall, Response.success(Left(error)))
            }
        }
    )

    override fun isExecuted(): Boolean = delegate.isExecuted

    override fun clone(): Call<Either<AppError, R>> = EitherCall(delegate.clone(), successType)

    override fun isCanceled(): Boolean = delegate.isCanceled

    override fun cancel() = delegate.cancel()

    override fun execute(): Response<Either<AppError, R>> = throw UnsupportedOperationException()

    override fun request(): Request = delegate.request()

    override fun timeout(): Timeout = delegate.timeout()

}