package com.example.nisumpoc.domain.repository

import com.example.nisumpoc.data.model.UserResponse
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface UserRepository {
   suspend fun getUserList(): Flow<Response<UserResponse>>
}
