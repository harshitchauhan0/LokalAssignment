package com.harshit.lokalassignment.data.models

import com.google.gson.annotations.SerializedName

data class Creative(
    @SerializedName("creative_type")
    val creativeType: Int? = null,

    @SerializedName("file")
    val file: String? = null,

    @SerializedName("image_url")
    val imageUrl: String? = null,

    @SerializedName("order_id")
    val orderId: Int? = null,

    @SerializedName("thumb_url")
    val thumbUrl: String? = null
)