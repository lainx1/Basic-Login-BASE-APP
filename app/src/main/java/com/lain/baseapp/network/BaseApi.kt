package com.lain.baseapp.network

import arrow.core.Either
import com.lain.baseapp.model.Model
import com.lain.baseapp.network.model.ApiError
import retrofit2.http.GET
import retrofit2.http.Path

const val BASE_URL = "https://pokeapi.co/api/v2/"
const val ENDPOINT = "pokemon/{name}"
interface BaseApi{

    @GET(value = ENDPOINT)
    suspend fun requestGet(@Path("name") name: String): Either<ApiError, Model>

}