package com.harshit.lokalassignment.data.models
import com.google.gson.annotations.SerializedName

data class PrimaryDetails(
    @SerializedName("Experience")
    val experience: String? = null,

    @SerializedName("Fees_Charged")
    val feesCharged: String? = null,

    @SerializedName("Job_Type")
    val jobType: String? = null,

    @SerializedName("Place")
    val place: String? = null,

    @SerializedName("Qualification")
    val qualification: String? = null,

    @SerializedName("Salary")
    val salary: String? = null
)
