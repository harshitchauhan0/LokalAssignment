package com.harshit.lokalassignment.data.models

import com.google.gson.annotations.SerializedName

data class Creative(
    @SerializedName("creative_type")
    val creativeType: Int,

    @SerializedName("file")
    val file: String,

    @SerializedName("image_url")
    val imageUrl: String,

    @SerializedName("order_id")
    val orderId: Int,

    @SerializedName("thumb_url")
    val thumbUrl: String
)