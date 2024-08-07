package com.harshit.lokalassignment.data.models

import com.google.gson.annotations.SerializedName

data class JobList(
    @SerializedName("results")
    val jobs: List<Jobs>? = null
)