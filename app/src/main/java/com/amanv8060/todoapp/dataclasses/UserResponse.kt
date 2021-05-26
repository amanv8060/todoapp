package com.amanv8060.todoapp.dataclasses

data class UserResponse(
    val avtarUrl: String,
    val email: String,
    val id: String,
    val name: String,
    val token: String
)