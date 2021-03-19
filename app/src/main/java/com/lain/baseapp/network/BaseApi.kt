package com.lain.baseapp.network

import arrow.core.Either
import com.lain.baseapp.model.Model
import com.lain.baseapp.network.model.AppError
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * This class is the base api, ready to use only changing the [BASE_URL], and defining new [ENDPOINT].
 */
const val BASE_URL = "https://pokeapi.co/api/v2/"
const val ENDPOINT = "pokemon/{name}"
interface BaseApi{

    /**
     * A example of a get request.
     * @param name: any name.
     * @return Either: the api response.
     */
    @GET(value = ENDPOINT)
    suspend fun requestGet(@Path("name") name: String): Either<AppError, Model>

}