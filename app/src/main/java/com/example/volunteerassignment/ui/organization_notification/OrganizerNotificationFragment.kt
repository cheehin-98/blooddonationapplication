package com.example.volunteerassignment.ui.organization_notification


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.volunteerassignment.R

class OrganizerNotificationFragment : Fragment() {

    private lateinit var OrganizerNotificationFragment: OrganizerNotificationViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        OrganizerNotificationFragment =
            ViewModelProviders.of(this).get(OrganizerNotificationViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_slideshow, container, false)
        val textView: TextView = root.findViewById(R.id.text_slideshow)
        OrganizerNotificationFragment.text.observe(this, Observer {
            textView.text = it
        })
        return root
    }

}
