package com.example.volunteerassignment.ui.organization_activity

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class OrganizerActivityViewModel : ViewModel() {
    private val _text = MutableLiveData<String>().apply {
        value = "This is organizer activity Fragment"
    }
    val text: LiveData<String> = _text
}