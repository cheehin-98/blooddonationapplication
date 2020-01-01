package com.example.volunteerassignment.ui.user_account

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.viewpager.widget.ViewPager
import com.example.volunteerassignment.R
import com.example.volunteerassignment.ui.user_point_and_redeem.UserPointAndRedeem_History
import com.example.volunteerassignment.ui.user_point_and_redeem.UserPointAndRedeem_Reward
import com.google.android.material.tabs.TabLayout

/**
 * A simple [Fragment] subclass.
 */
class UserAccountFragment : Fragment() {

    private lateinit var UserAccountFragment: UserAccountViewModel


    private lateinit var viewpager: ViewPager
    private lateinit var tabLayout: TabLayout

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        UserAccountFragment =
            ViewModelProviders.of(this).get(UserAccountViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_user_my_account, container, false)

        tabLayout = root.findViewById(R.id.tabLayout)
        viewpager = root.findViewById(R.id.viewPager)

        setupPager()
        integrateTabWithPager()
        return root
    }
    private fun setupPager() {
        viewpager.setAdapter(fragmentPagerAdapter(childFragmentManager))
    }

    private fun integrateTabWithPager() {
        tabLayout.setupWithViewPager(viewpager)
    }

    class fragmentPagerAdapter(manager: FragmentManager) : FragmentPagerAdapter(manager,
        FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT){

        override fun getItem(position: Int): Fragment {
            return when (position) {
                1 -> {
                    UserAccountFollow()
                }
                2 ->{
                    UserAccountAttend()
                }
                else -> {
                    UserAccountAchieve()
                }
            }
        }

        override fun getCount(): Int { // number of views
            return 3
        }

        override fun getPageTitle(position: Int): CharSequence? {
            val TAB_TITLES = arrayOf(
                "Achievement",
                "Follow",
                "Attendance"
            )
            return TAB_TITLES[position]
        }
    }
}