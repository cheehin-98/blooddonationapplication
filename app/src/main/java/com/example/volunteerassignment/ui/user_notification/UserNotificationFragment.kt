package com.example.volunteerassignment.ui.user_notification


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.volunteerassignment.R

class UserNotificationFragment : Fragment() {

    private lateinit var UserNotificationFragment: UserNotificationViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        UserNotificationFragment =
            ViewModelProviders.of(this).get(UserNotificationViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_slideshow, container, false)
        val textView: TextView = root.findViewById(R.id.text_slideshow)
        UserNotificationFragment.text.observe(this, Observer {
            textView.text = it
        })
        return root
    }
}
