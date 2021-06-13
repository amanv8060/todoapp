package com.amanv8060.todoapp.api

import com.amanv8060.todoapp.dataclasses.UserResponse
import retrofit2.Call

import retrofit2.Callback

class RestApiService(private val api: RestApi) : BaseRepository() {

    suspend fun signin(
        email: String,
        password: String
    ) = safeApiCall {
        api.signin(email, password)
    }

//    fun addUser(email: String, password:String, ) -> Unit){
//        val retrofit = ServiceBuilder.buildApi(RestApi::class.java)
//        retrofit.addUser(email,password).enqueue(
//            object : Callback<String,String> {
//                override fun onFailure(call: Call<UserResponse>, t: Throwable) {
//                    onResult(null)
//                }
//                override fun onResponse( call: Call<UserInfo>, response: UserResponse) {
//                    val addedUser = response.body()
//                    onResult(addedUser)
//                }
//            }
//        )
//    }
}