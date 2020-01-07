package com.example.volunteerassignment.ui.signup

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SignUpViewModel: ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is SignUp Fragment"
    }
    val text: LiveData<String> = _text
}
