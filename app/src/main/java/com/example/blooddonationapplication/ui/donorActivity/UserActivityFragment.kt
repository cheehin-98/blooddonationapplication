package com.example.blooddonationapplication.ui.donorActivity


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import com.example.blooddonationapplication.R
import com.google.android.material.tabs.TabLayout

class UserActivityFragment : Fragment() {


    private lateinit var viewpager: ViewPager
    private lateinit var tabLayout: TabLayout



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_user_my_activity, container, false)

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
                1 ->{
                    ActivityHistory()
                }else -> {
                    ActivityCommingSoon()
                }
            }
        }

        override fun getCount(): Int { // number of views
            return 2
        }

        override fun getPageTitle(position: Int): CharSequence? {
            val TAB_TITLES = arrayOf(
                "Event",
                "Event History"
            )
            return TAB_TITLES[position]
        }
    }
}