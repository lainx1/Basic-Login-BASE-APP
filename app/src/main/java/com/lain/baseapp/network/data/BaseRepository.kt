package com.lain.baseapp.network.data

import arrow.core.Either
import com.lain.baseapp.model.Model
import com.lain.baseapp.network.BaseApi
import com.lain.baseapp.network.model.AppError
import javax.inject.Inject

/**
 * This class is a base functional repository.
 * @param baseApi: a base api instance.
 */
class BaseRepository @Inject constructor(private val baseApi: BaseApi){

    /**
     * Send a functional sample to an api.
     * @param name: any name.
     * @return Either: the api response
     */
    suspend fun requestGet(name: String): Either<AppError, Model>{
        return baseApi.requestGet(name = name)
    }
}