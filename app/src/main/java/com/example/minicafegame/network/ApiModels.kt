package com.example.minicafegame.network

data class RegisterRequest(val username: String, val email: String, val password: String)
data class LoginRequest(val email: String, val password: String)
data class AuthResponse(val token: String, val username: String)
data class AddPointRequest(val amount: Int, val reason: String)
data class PointEntry(val amount: Int, val reason: String, val createdAt: Long)
data class PointsResponse(val totalPoints: Int, val history: List<PointEntry>)
