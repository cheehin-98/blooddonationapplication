package com.example.blooddonationapplication.ui.donorNotification


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.blooddonationapplication.R

class UserNotificationFragment : Fragment() {

    private lateinit var UserNotificationFragment: UserNotificationViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        UserNotificationFragment =
            ViewModelProviders.of(this).get(UserNotificationViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_user_notification, container, false)
        val textView: TextView = root.findViewById(R.id.text_user_notification)
        UserNotificationFragment.text.observe(this, Observer {
            textView.text = it
        })
        return root
    }

//    companion object{
//        private val ARG_PARAM1 = "param1"
//        private val ARG_PARAM2 = "param2"
//
//        fun newInstance(): UserNotificationFragment {
//            val fragment = UserNotificationFragment()
//            val args = Bundle()
//
//            return fragment
//        }
//    }
}
