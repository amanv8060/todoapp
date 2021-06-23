package com.amanv8060.todoapp.auth

import android.os.Bundle
import android.os.Looper
import android.util.Log
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.navigation.fragment.findNavController
import com.amanv8060.todoapp.R
import com.amanv8060.todoapp.api.Resource
import com.amanv8060.todoapp.api.RestApi
import com.amanv8060.todoapp.api.RestApiService
import com.amanv8060.todoapp.api.ServiceBuilder
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonParser
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class LoginFragment : Fragment() {


    private val serviceBuilder = ServiceBuilder()
    private val retrofit = RestApiService(serviceBuilder.buildApi(RestApi::class.java))

    private lateinit var registerNow: TextView
    private lateinit var progressBar: ProgressBar
    private lateinit var loginButton: Button
    private lateinit var emailText: EditText
    private lateinit var passwordText: EditText

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val rootView: View = inflater.inflate(R.layout.fragment_login, container, false)
        setUp(rootView)
        setupListeners()
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
                progressBar.visibility = View.VISIBLE
                loginButton.isEnabled = false
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
                            withContext(Dispatchers.Main) {
                                progressBar.visibility = View.GONE
                            }
                        }
                        is Resource.Failure -> {
                            if (temp.isNetworkError) {
                                Toast.makeText(
                                    requireContext(),
                                    "Network Error",
                                    Toast.LENGTH_LONG
                                ).show()
                            } else {
                                val string =
                                    JsonParser.parseString(temp.errorBody?.string()).asJsonObject
                                Toast.makeText(
                                    requireContext(),string["message"].asString,
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                            withContext(Dispatchers.Main) {
                                progressBar.visibility = View.GONE
                                loginButton.isEnabled = true
                            }
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