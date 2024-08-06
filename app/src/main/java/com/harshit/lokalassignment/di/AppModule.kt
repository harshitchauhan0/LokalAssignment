package com.harshit.lokalassignment.di

import android.content.Context
import com.harshit.lokalassignment.data.remote.JobsApi
import com.harshit.lokalassignment.data.remote.JobsRepository
import com.harshit.lokalassignment.utils.Constants
import com.harshit.lokalassignment.utils.network.NetworkUtils
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule{

    @Singleton
    @Provides
    fun provideNetworkUtils(@ApplicationContext context: Context): NetworkUtils {
        return NetworkUtils(context)
    }

    @Singleton
    @Provides
    fun provideJobsApi(): JobsApi{
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(JobsApi::class.java)
    }

    @Singleton
    @Provides
    fun provideJobsRepository(api: JobsApi,networkUtils: NetworkUtils): JobsRepository {
        return JobsRepository(api,networkUtils)
    }
}