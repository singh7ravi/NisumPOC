package com.example.nisumpoc.domain.repository

import com.example.nisumpoc.data.model.UserResponse
import com.example.nisumpoc.data.network.ApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.Response
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(private val apiService: ApiService) :
    UserRepository {

    override suspend fun getUserList(): Flow<Response<UserResponse>> = flow{
        emit(apiService.getUsers())
    }.flowOn(Dispatchers.IO)

}
