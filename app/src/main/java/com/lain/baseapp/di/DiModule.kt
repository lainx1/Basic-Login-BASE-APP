package com.lain.baseapp.di

import com.lain.baseapp.network.BASE_URL
import com.lain.baseapp.network.BaseApi
import com.lain.baseapp.network.converters.EitherCallAdapterFactory
import com.lain.baseapp.network.data.BaseRepository
import com.lain.baseapp.viewmodel.BaseViewModel
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

/**
 * This class contains the hilt dependency injection module.
 */
@Module
@InstallIn(ActivityComponent::class)
object DiModule {

    /**
     * Provides base api.
     * @return BaseApi: instance
     */
    @Provides
    fun provideBaseApi() : BaseApi{
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create(
                Moshi.Builder().add(
                    KotlinJsonAdapterFactory()
                ).build()
            )).addCallAdapterFactory(EitherCallAdapterFactory())
            .build()

        return retrofit.create(BaseApi::class.java)
    }

    /**
     * Provides base repository.
     * @param baseApi: base api instance.
     * @return BaseRepository: instance
     */
    @Provides
    fun providesBaseRepository(baseApi: BaseApi): BaseRepository = BaseRepository(baseApi = baseApi)

    /**
     * Provides base view model.
     * @param baseBaseRepository: base repository instance.
     * @return BaseViewModel: instance
     */
    @Provides
    fun provideBaseViewModel(baseBaseRepository: BaseRepository): BaseViewModel = BaseViewModel(baseRepository = baseBaseRepository)

}