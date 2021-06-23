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
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SignUpFragment : Fragment() {

    private val serviceBuilder = ServiceBuilder()
    private val retrofit = RestApiService(serviceBuilder.buildApi(RestApi::class.java))

    private lateinit var loginNow: TextView
    private lateinit var progressBar: ProgressBar
    private lateinit var signUpButton: Button
    private lateinit var emailText: EditText
    private lateinit var passwordText: EditText
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val rootView: View = inflater.inflate(R.layout.fragment_sign_up, container, false)
        setUp(rootView)
        setupListeners()
        return rootView
    }

    private fun setupListeners() {
        loginNow.setOnClickListener {
            findNavController().navigate(R.id.action_signUpFragment_to_loginFragment)
        }
        signUpButton.setOnClickListener {
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
                    val temp = retrofit.signup(email, password)
                    if (Looper.myLooper() == null) {
                        Looper.prepare()
                    }
                    when (temp) {
                        is Resource.Success -> {
                            Toast.makeText(
                                requireContext(),
                                "SignUp Success , Please Login",
                                Toast.LENGTH_LONG
                            )
                                .show()
                            withContext(Dispatchers.Main) {
                                findNavController().navigate(R.id.action_signUpFragment_to_loginFragment)
                            }
                        }
                        is Resource.Failure -> {
                            Log.d(
                                "temp",
                                temp.errorBody.toString()
                            );
                            Toast.makeText(
                                requireContext(),
                                temp.errorBody.toString(),
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
        loginNow = rootView.findViewById(R.id.textViewLoginNow)
        progressBar = rootView.findViewById(R.id.progressBarSignupUp)
        emailText = rootView.findViewById(R.id.editTextSignupEmailAddress)
        passwordText = rootView.findViewById(R.id.editTextSignUpPassword)
        signUpButton = rootView.findViewById(R.id.buttonSignUp)
    }

}