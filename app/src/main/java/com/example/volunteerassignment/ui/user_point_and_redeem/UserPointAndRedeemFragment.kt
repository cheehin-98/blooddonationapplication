package com.example.volunteerassignment.ui.user_point_and_redeem

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.viewpager.widget.ViewPager
import com.example.volunteerassignment.R
import com.google.android.material.tabs.TabLayout


class UserPointAndRedeemFragment : Fragment() {

    private lateinit var UserPointAndRedeemFragment: UserPointAndRedeemViewModel

    private lateinit var viewpager:ViewPager
    private lateinit var tabLayout:TabLayout

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        UserPointAndRedeemFragment =
            ViewModelProviders.of(this).get(UserPointAndRedeemViewModel::class.java)

        val root = inflater.inflate(R.layout.fragment_user_point_and_redeem, container, false)

        tabLayout = root.findViewById(R.id.tabLayout)
        viewpager = root.findViewById(R.id.viewPager)
//        UserPointAndRedeemFragment.text.observe(this, Observer {
//            textView.text = it
//        })
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

    class fragmentPagerAdapter(manager: FragmentManager) : FragmentPagerAdapter(manager,FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT){


        override fun getItem(position: Int): Fragment {
            return when (position) {
                1 -> {
                    UserPointAndRedeem_History()
                }
                else -> {
                    UserPointAndRedeem_Reward()
                }
            }
            //return SubNewsFragment.newInstance(subTabNameCollection[position])
        }

        // Return the total number of fragment.
        override fun getCount(): Int { // number of views
            return 2
        }

        override fun getPageTitle(position: Int): CharSequence? {
            val TAB_TITLES = arrayOf(
                "Point & Reward",
                "History"
            )
            return TAB_TITLES[position]
        }
    }
}