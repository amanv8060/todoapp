package com.amanv8060.todoapp.api

import com.amanv8060.todoapp.dataclasses.SignUpResponse
import com.amanv8060.todoapp.dataclasses.UserResponse
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST
import retrofit2.http.PUT

interface RestApi {

    @FormUrlEncoded
    @POST("auth/user/signin/")
    suspend fun signin(
        @Field("email") email: String,
        @Field("password") password: String
    ): UserResponse

    @FormUrlEncoded
    @PUT("auth/user/signup/")
    suspend  fun signup(
        @Field("email") email: String,
        @Field("password") password: String,
        ):SignUpResponse
}