package com.harshit.lokalassignment.data.models
import com.google.gson.annotations.SerializedName

data class Location(
    @SerializedName("id")
    val id: Int? = null,

    @SerializedName("locale")
    val locale: String? = null,

    @SerializedName("state")
    val state: Int? = null
)
