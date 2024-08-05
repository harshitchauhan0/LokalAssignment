package com.harshit.lokalassignment.data.models

import com.google.gson.annotations.SerializedName

data class JobTag(
    @SerializedName("bg_color")
    val bgColor: String,

    @SerializedName("text_color")
    val textColor: String,

    @SerializedName("value")
    val value: String
)