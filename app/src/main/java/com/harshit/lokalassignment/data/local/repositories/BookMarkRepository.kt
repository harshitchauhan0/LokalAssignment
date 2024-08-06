package com.harshit.lokalassignment.data.local.repositories

import com.harshit.lokalassignment.data.local.BookMarkDao
import com.harshit.lokalassignment.data.models.Jobs
import com.harshit.lokalassignment.data.models.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class BookMarkRepository @Inject constructor(private val dao: BookMarkDao) {
    suspend fun insertJob(job: Jobs):Flow<Result<Boolean>> = flow {
        emit(Result.Loading<Boolean>())
        try{
            dao.insertJob(job)
            emit(Result.Success<Boolean>(true))
        }
        catch (e: Exception){
            emit(Result.Error<Boolean>(message = e.message.toString()))
        }
    }

    suspend fun deleteJob(job: Jobs):Flow<Result<Boolean>> = flow {
        emit(Result.Loading<Boolean>())
        try{
            dao.deleteJob(job)
            emit(Result.Success<Boolean>(true))
        }catch (e: Exception){
            emit(Result.Error<Boolean>(message = e.message.toString()))
        }
    }

    suspend fun getBookMarkedJobs(): Flow<Result<List<Jobs>>> = flow {
        emit(Result.Loading<List<Jobs>>())
        try {
            val list = dao.getBookMarkedJobs()
            emit(Result.Success<List<Jobs>>(list))
        }catch (e: Exception){
            emit(Result.Error<List<Jobs>>(message = e.message.toString()))
        }
    }
}