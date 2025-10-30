package com.data.request

data class UserRegisterRequest(
    val userName: String,
    val email: String,
    val password: String
)