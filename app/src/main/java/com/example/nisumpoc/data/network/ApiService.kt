package com.example.nisumpoc.data.network

import com.example.nisumpoc.data.model.UserResponse
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {
    @GET("api/?results=100")
    suspend fun getUsers(): Response<UserResponse>

}