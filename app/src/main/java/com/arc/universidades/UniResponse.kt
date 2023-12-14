package com.arc.universidades

import com.google.gson.annotations.SerializedName

data class UniResponse(@SerializedName("message") var message: List<Universidad>)

data class Universidad(
    var name: String,
    var country: String,
    var stateProvince: String?,
    var alphaCode: String,
    var webPages: List<String>,
    var domains: List<String>
)