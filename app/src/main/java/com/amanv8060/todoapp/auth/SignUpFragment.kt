package com.amanv8060.todoapp.auth

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import com.amanv8060.todoapp.R

class SignUpFragment : Fragment() {

    lateinit var loginNow: TextView;
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView: View = inflater.inflate(R.layout.fragment_sign_up, container, false)
        loginNow = rootView.findViewById(R.id.textViewLoginNow)

        loginNow.setOnClickListener(View.OnClickListener {
            findNavController().navigate(R.id.action_signUpFragment_to_loginFragment)
        }
        )
        // Inflate the layout for this fragment
        return rootView
    }
}