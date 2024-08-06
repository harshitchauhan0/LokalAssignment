package com.harshit.lokalassignment.data.remote

import com.harshit.lokalassignment.data.models.JobList
import com.harshit.lokalassignment.utils.network.NetworkUtils
import javax.inject.Inject
import com.harshit.lokalassignment.data.models.Result
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class JobsRepository @Inject constructor(
    private val api: JobsApi,
    private val networkUtils: NetworkUtils
) {
    suspend fun getJobList(page: Int): Flow<Result<JobList>> = flow{
        emit(Result.Loading<JobList>())
        if(!networkUtils.hasInternetConnection()){
            emit(Result.Error<JobList>("No internet connection"))
        }
        try {
            val list = api.getJobList(page)
            emit(Result.Success<JobList>(list))
        }
        catch (e: Exception){
             emit(Result.Error<JobList>("Something went wrong"))
        }
    }
}