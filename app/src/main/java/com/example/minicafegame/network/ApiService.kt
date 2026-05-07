package com.example.minicafegame.network

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface ApiService {

    @POST("register")
    suspend fun register(@Body request: RegisterRequest): String

    @POST("login")
    suspend fun login(@Body request: LoginRequest): AuthResponse

    @GET("points")
    suspend fun getPoints(@Header("Authorization") token: String): PointsResponse

    @POST("points/add")
    suspend fun addPoints(
        @Header("Authorization") token: String,
        @Body request: AddPointRequest
    ): String
}
