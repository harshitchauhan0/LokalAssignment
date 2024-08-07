package com.harshit.lokalassignment.data.models

import com.google.gson.annotations.SerializedName

data class ContactPreference(
    @SerializedName("preference")
    val preference: Int? = null,

    @SerializedName("preferred_call_end_time")
    val preferredCallEndTime: String? = null,

    @SerializedName("preferred_call_start_time")
    val preferredCallStartTime: String? = null,

    @SerializedName("whatsapp_link")
    val whatsappLink: String? = null
)