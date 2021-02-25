package com.lain.baseapp.network.model

data class HttpErrorResponse(
        val status: Int,
        val type: String,
        val message: String
)
