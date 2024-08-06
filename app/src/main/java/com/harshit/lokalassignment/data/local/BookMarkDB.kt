package com.harshit.lokalassignment.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.harshit.lokalassignment.data.models.Jobs

@Database(entities = [Jobs::class], version = 1, exportSchema = false)
@TypeConverters(Convertors::class)
abstract class BookMarkDB: RoomDatabase() {
    abstract val dao: BookMarkDao
}