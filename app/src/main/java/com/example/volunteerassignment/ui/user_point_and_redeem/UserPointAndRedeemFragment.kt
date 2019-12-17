package com.example.volunteerassignment.ui.user_point_and_redeem

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.volunteerassignment.R

class UserPointAndRedeemFragment : Fragment() {

    private lateinit var UserPointAndRedeemFragment: UserPointAndRedeemViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        UserPointAndRedeemFragment =
            ViewModelProviders.of(this).get(UserPointAndRedeemViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_user_point_and_redeem, container, false)
        val textView: TextView = root.findViewById(R.id.text_user_point_and_redeem)
        UserPointAndRedeemFragment.text.observe(this, Observer {
            textView.text = it
        })
        return root
    }
}