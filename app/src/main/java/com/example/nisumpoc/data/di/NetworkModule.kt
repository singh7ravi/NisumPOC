package com.example.nisumpoc.data.di

import com.example.nisumpoc.data.network.ApiService
import com.example.nisumpoc.domain.utils.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

   @Provides
   fun provideRetrofit(): Retrofit {
       return Retrofit.Builder()
           .baseUrl(BASE_URL)
           .addConverterFactory(GsonConverterFactory.create())
           .build()
   }

    @Singleton
    @Provides
    fun getUserServiceApi(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }
}