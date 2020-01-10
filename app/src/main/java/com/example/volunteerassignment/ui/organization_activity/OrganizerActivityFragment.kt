package com.example.volunteerassignment.ui.organization_activity


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import com.example.volunteerassignment.R
import com.google.android.material.tabs.TabLayout

/**
 * A simple [Fragment] subclass.
 */
class OrganizerActivityFragment : Fragment() {

    private lateinit var viewpager: ViewPager
    private lateinit var tabLayout: TabLayout

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val root = inflater.inflate(R.layout.fragment_organization_my_activity, container, false)

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
        FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

        override fun getItem(position: Int): Fragment {
            return when (position) {

                0 -> {
                    OrganizerCreateEventFragment()
                }
                1 -> {
                    OrganizerUpdateEventFragmentRecycleView()
                }
                else ->{
                    OrganizerEventOnTodayFragmentRecycleView()
                }
            }

        }

        override fun getCount(): Int {
            return  3
        }

        override fun getPageTitle(position: Int): CharSequence? {
            val TAB_TITLES = arrayOf(
                "Create Event",
                "Update Event",
                "Today Event"
            )
            return TAB_TITLES[position]
        }
    }
}
