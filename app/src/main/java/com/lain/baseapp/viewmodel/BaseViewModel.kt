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


class BaseViewModel @Inject constructor(private val baseRepository: BaseRepository): ViewModel(){

    private val _error = MutableLiveData<ApiError>()
    val error: LiveData<ApiError> get() = _error

    private val _loading = MutableLiveData<Boolean>()
    val loading : LiveData<Boolean> get() = _loading

    private val _model = MutableLiveData<Model>()
    val model: LiveData<Model> get() = _model

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