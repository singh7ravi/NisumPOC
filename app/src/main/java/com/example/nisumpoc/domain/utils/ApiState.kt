package com.example.nisumpoc.domain.utils


import com.example.nisumpoc.data.model.UserResponse
import retrofit2.Response

sealed class ApiState {

    class Success(val data: Response<UserResponse>) : ApiState()
    class Failure(val msg: Throwable) : ApiState()
    object Loading : ApiState()
    object Empty : ApiState()
}