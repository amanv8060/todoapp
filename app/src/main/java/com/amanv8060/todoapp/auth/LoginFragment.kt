package com.amanv8060.todoapp.auth

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.amanv8060.todoapp.R
import com.amanv8060.todoapp.api.Resource
import com.amanv8060.todoapp.api.RestApi
import com.amanv8060.todoapp.api.RestApiService
import com.amanv8060.todoapp.api.ServiceBuilder
import com.amanv8060.todoapp.dataclasses.UserResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class LoginFragment : Fragment() {


    protected val serviceBuilder = ServiceBuilder()
    val retrofit = RestApiService(serviceBuilder.buildApi(RestApi::class.java))



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        CoroutineScope(Dispatchers.IO).launch {
            Log.d("Thread", "Called")
            val temp=retrofit.signin("verma1090aman@gmail.com", "12345678")
            Log.d("Response", temp.toString())
            Log.d("Thread", "Left")
        }
//            Log.d("Scope",temp.execute().toString())

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

}