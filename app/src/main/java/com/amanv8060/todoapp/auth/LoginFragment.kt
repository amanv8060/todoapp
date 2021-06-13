package com.amanv8060.todoapp.auth

import android.content.Intent
import android.os.Bundle
import android.os.Looper
import android.util.Log
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.navigation.fragment.findNavController
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

    lateinit var registerNow: TextView
    lateinit var progressBar: ProgressBar
    lateinit var loginButton: Button
    lateinit var emailText: EditText
    lateinit var passwordText: EditText

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView: View = inflater.inflate(R.layout.fragment_login, container, false)
        setUp(rootView)
        setupListeners()


//            Log.d("Scope",temp.execute().toString())

        // Inflate the layout for this fragment
        return rootView
    }

    private fun setupListeners() {
        registerNow.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_signUpFragment)
        }

        loginButton.setOnClickListener {

            val email = emailText.text.toString()
            val password = passwordText.text.toString()

            if (!email.isValidEmail()) {
                emailText.error = "Enter Valid Email"
            } else if (password.isNullOrEmpty() || password.length < 8) {
                emailText.error = null
                passwordText.error = "Enter Valid Password"
            } else {
                passwordText.error = null
                CoroutineScope(Dispatchers.IO).launch {

                    Log.d("Thread", "Called")
                    val temp = retrofit.signin(email, password)
                    if (Looper.myLooper() == null) {
                        Looper.prepare()
                    }
                    when (temp) {
                        is Resource.Success -> {
                            Toast.makeText(requireContext(), temp.value.email, Toast.LENGTH_LONG)
                                .show()
                        }
                        is Resource.Failure -> {

                            Toast.makeText(
                                requireContext(),
                                temp.errorCode.toString(),
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }
                    Log.d("Thread", "Left")
                    Looper.loop()

                }
            }
        }
    }

    private fun CharSequence?.isValidEmail() =
        !isNullOrEmpty() && Patterns.EMAIL_ADDRESS.matcher(this).matches()

    private fun setUp(rootView: View) {
        registerNow = rootView.findViewById(R.id.textViewRegisterNow)
        progressBar = rootView.findViewById(R.id.progressBarLogIn)
        emailText = rootView.findViewById(R.id.editTextSignInEmailAddress)
        passwordText = rootView.findViewById(R.id.editTextSignInPassword)
        loginButton = rootView.findViewById(R.id.buttonLogin)


    }

}