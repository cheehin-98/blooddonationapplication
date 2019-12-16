package com.example.volunteerassignment.ui.user_account

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
class UserAccountFragment : Fragment() {

    private lateinit var UserAccountFragment: UserAccountViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        UserAccountFragment =
            ViewModelProviders.of(this).get(UserAccountViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_slideshow, container, false)
        val textView: TextView = root.findViewById(R.id.text_slideshow)
        UserAccountFragment.text.observe(this, Observer {
            textView.text = it
        })
        return root
    }

}
