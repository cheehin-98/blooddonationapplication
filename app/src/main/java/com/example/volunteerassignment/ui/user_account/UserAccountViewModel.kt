package com.example.volunteerassignment.ui.user_account

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class UserAccountViewModel: ViewModel() {
    private val _text = MutableLiveData<String>().apply {
        value = "This is user account Fragment"
    }
    val text: LiveData<String> = _text
}