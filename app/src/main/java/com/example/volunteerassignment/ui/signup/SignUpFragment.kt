package com.example.volunteerassignment.ui.signup


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.volunteerassignment.R

/**
 * A simple [Fragment] subclass.
 */
class SignUpFragment : Fragment() {

    private lateinit var signupViewModel: SignUpViewModel
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        signupViewModel =
            ViewModelProviders.of(this).get(SignUpViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_signup, container, false)
        val textView: TextView = root.findViewById(R.id.text_signup)
        signupViewModel.text.observe(this, Observer {
            textView.text = it
        })
        return root
    }
}