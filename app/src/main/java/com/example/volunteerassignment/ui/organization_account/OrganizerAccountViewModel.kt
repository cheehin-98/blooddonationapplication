package com.example.volunteerassignment.ui.organization_account

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class OrganizerAccountViewModel : ViewModel() {
    private val _text = MutableLiveData<String>().apply {
        value = "This is organizer account Fragment"
    }
    val text: LiveData<String> = _text
}