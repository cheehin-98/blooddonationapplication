package com.example.volunteerassignment.ui.user_activity


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.volunteerassignment.R

class UserActivityFragment : Fragment() {

    private lateinit var UserActivityFragment: UserActivityViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        UserActivityFragment =
            ViewModelProviders.of(this).get(UserActivityViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_user_my_activity, container, false)
        val textView: TextView = root.findViewById(R.id.text_user_activity)
        UserActivityFragment.text.observe(this, Observer {
            textView.text = it
        })
        return root
    }

//    companion object{
//        private val ARG_PARAM1 = "param1"
//        private val ARG_PARAM2 = "param2"
//
//        fun newInstance(): UserActivityFragment {
//            val fragment = UserActivityFragment()
//            val args = Bundle()
//
//            return fragment
//        }
//    }
}
