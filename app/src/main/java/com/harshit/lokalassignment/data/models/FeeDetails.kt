package com.harshit.lokalassignment.data.models

import com.google.gson.annotations.SerializedName

data class FeeDetails(
    @SerializedName("V3")
    val V3: List<V3>? = null
)