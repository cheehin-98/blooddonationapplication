package com.example.volunteerassignment.ui.user_notification

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class UserNotificationViewModel : ViewModel() {
    private val _text = MutableLiveData<String>().apply {
        value = "This is user notification Fragment"
    }
    val text: LiveData<String> = _text
}