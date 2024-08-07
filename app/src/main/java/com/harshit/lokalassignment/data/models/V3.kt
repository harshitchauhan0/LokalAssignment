package com.harshit.lokalassignment.data.models
import com.google.gson.annotations.SerializedName

data class V3(
    @SerializedName("field_key")
    val fieldKey: String? = null,

    @SerializedName("field_name")
    val fieldName: String? = null,

    @SerializedName("field_value")
    val fieldValue: String? = null
)
