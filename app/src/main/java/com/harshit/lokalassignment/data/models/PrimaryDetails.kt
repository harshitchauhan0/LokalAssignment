package com.harshit.lokalassignment.data.models
import com.google.gson.annotations.SerializedName

data class PrimaryDetails(
    @SerializedName("Experience")
    val experience: String,

    @SerializedName("Fees_Charged")
    val feesCharged: String,

    @SerializedName("Job_Type")
    val jobType: String,

    @SerializedName("Place")
    val place: String,

    @SerializedName("Qualification")
    val qualification: String,

    @SerializedName("Salary")
    val salary: String
)
