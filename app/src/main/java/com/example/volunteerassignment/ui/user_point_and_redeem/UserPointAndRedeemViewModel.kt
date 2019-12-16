package com.example.volunteerassignment.ui.user_point_and_redeem

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class UserPointAndRedeemViewModel: ViewModel() {
    private val _text = MutableLiveData<String>().apply {
        value = "This is user point and redeem Fragment"
    }
    val text: LiveData<String> = _text
}