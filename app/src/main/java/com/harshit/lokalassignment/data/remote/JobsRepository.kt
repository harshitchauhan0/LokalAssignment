package com.harshit.lokalassignment.data.remote

import com.harshit.lokalassignment.data.local.BookMarkDao
import com.harshit.lokalassignment.data.models.JobList
import com.harshit.lokalassignment.data.models.Jobs
import com.harshit.lokalassignment.data.models.Result
import com.harshit.lokalassignment.utils.network.NetworkUtils
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class JobsRepository @Inject constructor(
    private val api: JobsApi,
    private val networkUtils: NetworkUtils,
    private val dao: BookMarkDao
) {
    suspend fun getJobList(page: Int): Flow<Result<JobList>> = flow {
        emit(Result.Loading<JobList>())
        if (!networkUtils.hasInternetConnection()) {
            emit(Result.Error<JobList>("No internet connection"))
        } else {
            try {
                val list = api.getJobList(page)
                emit(Result.Success<JobList>(list))
            } catch (e: Exception) {
                emit(Result.Error<JobList>("Something went wrong"))
            }
        }
    }

    suspend fun insertJob(job: Jobs): Flow<Result<Boolean>> = flow {
        emit(Result.Loading<Boolean>())
        try {
            dao.insertJob(job)
            emit(Result.Success<Boolean>(true))
        } catch (e: Exception) {
            emit(Result.Error<Boolean>(message = e.message.toString()))
        }
    }
}