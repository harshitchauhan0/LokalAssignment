package com.harshit.lokalassignment.data.remote

import com.harshit.lokalassignment.data.models.JobList
import retrofit2.http.GET
import retrofit2.http.Query

interface JobsApi {
    @GET("common/jobs")
    suspend fun getJobList(@Query("page") page : Int):JobList
}