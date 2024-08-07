package com.harshit.lokalassignment.data.models

import com.google.gson.annotations.SerializedName

data class JobTag(
    @SerializedName("bg_color")
    val bgColor: String? = null,

    @SerializedName("text_color")
    val textColor: String? = null,

    @SerializedName("value")
    val value: String? = null
)