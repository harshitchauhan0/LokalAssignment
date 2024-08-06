package com.harshit.lokalassignment.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.harshit.lokalassignment.data.models.Jobs

@Dao
interface BookMarkDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertJob(job: Jobs)

    @Delete
    suspend fun deleteJob(job: Jobs)

    @Query("SELECT * FROM jobs")
    suspend fun getBookMarkedJobs(): List<Jobs>
}