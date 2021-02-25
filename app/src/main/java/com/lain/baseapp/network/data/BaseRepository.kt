package com.lain.baseapp.network.data

import arrow.core.Either
import com.lain.baseapp.model.Model
import com.lain.baseapp.network.BaseApi
import com.lain.baseapp.network.model.ApiError
import javax.inject.Inject

class BaseRepository @Inject constructor(private val baseApi: BaseApi){

    suspend fun requestGet(name: String): Either<ApiError, Model>{
        return baseApi.requestGet(name = name)
    }
}