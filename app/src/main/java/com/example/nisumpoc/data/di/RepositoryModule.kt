package com.example.nisumpoc.data.di

import com.example.nisumpoc.data.network.ApiService
import com.example.nisumpoc.domain.repository.UserRepository
import com.example.nisumpoc.domain.repository.UserRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideCryptoCoinRepository(apiService: ApiService): UserRepository {
        return UserRepositoryImpl(apiService)
    }
}
