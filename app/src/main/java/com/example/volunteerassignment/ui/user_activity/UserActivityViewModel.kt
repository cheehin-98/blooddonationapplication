package com.example.volunteerassignment.ui.user_activity

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class UserActivityViewModel: ViewModel() {
    private val _text = MutableLiveData<String>().apply {
        value = "This is user activity Fragment"
    }
    val text: LiveData<String> = _text
}