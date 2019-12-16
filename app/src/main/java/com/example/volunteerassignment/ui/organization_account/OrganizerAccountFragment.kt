package com.example.volunteerassignment.ui.organization_account


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.volunteerassignment.R


class OrganizerAccountFragment : Fragment() {

    private lateinit var OrganizerAccountFragment: OrganizerAccountViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        OrganizerAccountFragment =
            ViewModelProviders.of(this).get(OrganizerAccountViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_slideshow, container, false)
        val textView: TextView = root.findViewById(R.id.text_slideshow)
        OrganizerAccountFragment.text.observe(this, Observer {
            textView.text = it
        })
        return root
    }


}
