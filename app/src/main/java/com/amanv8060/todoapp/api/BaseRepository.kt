package com.amanv8060.todoapp.api

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException

abstract class BaseRepository {
    suspend fun <T> safeApiCall(
        apiCall: suspend () -> T
    ): Resource<T> {

        Log.d("Base Repo", "Reached")
        return withContext(
            Dispatchers.IO
        ) {
            try {

                Log.d("Base Repo", "PQE")
                Resource.Success(apiCall.invoke())

            } catch (throwable: Throwable) {
                Log.d("BaseRepo", throwable.toString())
                when (throwable) {
                    is HttpException -> {
                        Log.d("Base Repo", "Xyz")
                        Resource.Failure(false, throwable.code(), throwable.response()?.errorBody())
                    }
                    else -> {
                        Log.d("Base Repo", "Network Error")
                        Resource.Failure(true, null, null)
                    }
                }
            }
        }
    }
}