package com.lain.baseapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lain.baseapp.model.Model
import com.lain.baseapp.network.data.BaseRepository
import com.lain.baseapp.network.model.ApiError
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * This class is a base view model, functional and ready to use.
 *
 * @param baseRepository: Example repository.
 */
class BaseViewModel @Inject constructor(private val baseRepository: BaseRepository): ViewModel(){

    /**
     * A live data error.
     */
    private val _error = MutableLiveData<ApiError>()

    /**
     * A live data for loading.
     */
    private val _loading = MutableLiveData<Boolean>()

    /**
     * The response live data.
     */
    private val _model = MutableLiveData<Model>()

    /**
     * The entry to error.
     */
    val error: LiveData<ApiError> get() = _error

    /**
     * The entry to loading data.
     */
    val loading : LiveData<Boolean> get() = _loading

    /**
     * The entry to response.
     */
    val model: LiveData<Model> get() = _model

    /**
     * Send a functional get request example.
     * @param name: a sample name.
     */
    fun requestGet(name: String){
        _loading.value = true

        viewModelScope.launch(Dispatchers.IO) {
            val either = baseRepository.requestGet(name = name)

            withContext(Dispatchers.Main){
                _loading.value = false

                either.fold({
                    _error.value = it
                },{
                    _model.value = it
                })
            }

        }
    }
}