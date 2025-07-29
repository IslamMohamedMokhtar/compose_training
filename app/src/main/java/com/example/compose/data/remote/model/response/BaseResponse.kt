package com.example.compose.data.remote.model.response

import com.google.gson.annotations.SerializedName

open class BaseResponse<T>(
    @SerializedName("Messages")
    val message: List<String>? = null,
    @SerializedName("Results")
    val data: T? = null,
    @SerializedName("Errors")
    val errors: List<String>? = null

)
