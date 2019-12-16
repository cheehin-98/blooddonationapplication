package com.example.volunteerassignment.ui.organization_activity


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.volunteerassignment.R
/**
 * A simple [Fragment] subclass.
 */
class OrganizerActivityFragment : Fragment() {

    private lateinit var OrganizerActivityFragment: OrganizerActivityViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        OrganizerActivityFragment =
            ViewModelProviders.of(this).get(OrganizerActivityViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_slideshow, container, false)
        val textView: TextView = root.findViewById(R.id.text_slideshow)
        OrganizerActivityFragment.text.observe(this, Observer {
            textView.text = it
        })
        return root
    }

}
