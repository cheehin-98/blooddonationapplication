package com.example.volunteerassignment.ui.user_account


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.example.volunteerassignment.R

/**
 * A simple [Fragment] subclass.
 */
class UserAccountAttend : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_user_account_attend, container, false)
    }


}
