package com.lain.baseapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.lain.baseapp.network.model.AppError
import com.lain.baseapp.network.model.InternalAppError
import java.util.*

class LoginViewModel : ViewModel() {

    /**
     * A live data error.
     */
    private val _error = MutableLiveData<AppError>()

    /**
     * A live data for loading.
     */
    private val _loading = MutableLiveData<Boolean>()

    /**
     * A live data for user.
     */
    private val _user = MutableLiveData<String>()

    /**
     * The entry to error.
     */
    val error: LiveData<AppError> get() = _error

    /**
     * The entry to loading data.
     */
    val loading : LiveData<Boolean> get() = _loading

    /**
     * The entry to user data.
     */
    val user: LiveData<String> get() = _user


    /**
     * Handle login
     * @param email: the email of the user.
     * @param password: the password of the user.
     */
    fun login(email: String, password: String){
        /**
         * Start loader
         */
        _loading.value = true

        /**
         * Validate email of password is not empty.
         */
        if(email.isEmpty() || password.isEmpty()){
            _loading.value = false
            val internalError = InternalAppError(message =  "Credenciales incorrectas.")
            _error.value = internalError

            return
        }

        /**
         * Validate well formed email.
         */
        val regexEmail = "(?:[a-z0-9!#\$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#\$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])"
        val regex = Regex(regexEmail)
        if(!regex.matches(email.toLowerCase(Locale.ROOT))) {

            /**
             * Stop loading
             */
            _loading.value = false

            val internalError = InternalAppError(message =  "Email no valido.")
            _error.value = internalError

            return
        }

        /**
         * Validate correct credentials
         */
        val user = "abcde@mail.com"
        val pass = "1234"
        if(!(email.toLowerCase(Locale.ROOT) == user && password.toLowerCase(Locale.ROOT) == pass)){

            /**
             * Stop loading
             */
            _loading.value = false

            val internalError = InternalAppError(message =  "Credenciales incorrectas.")
            _error.value = internalError

            return
        }

        /**
         * Set user loaded
         */
        _user.value = email

        /**
         * Stop loading
         */
        _loading.value = false

    }

}