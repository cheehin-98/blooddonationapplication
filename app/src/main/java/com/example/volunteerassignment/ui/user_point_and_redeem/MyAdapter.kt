package com.example.volunteerassignment.ui.user_point_and_redeem

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.volunteerassignment.R

private val TAB_TITLES = arrayOf(
    R.string.reward,
    R.string.history
)
class MyAdapter(private val context: Context, fm: FragmentManager) : FragmentPagerAdapter(fm,FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    override fun getItem(position: Int): Fragment { // get the fragment
        return when (position) {
            1 -> {
                UserPointAndRedeem_History()
            }
            else -> {
                UserPointAndRedeem_Reward()
            }
        }
    }
    override fun getPageTitle(position: Int): CharSequence? { // return tab title, must declare this function else no tab title
        return context.resources.getString(TAB_TITLES[position])
    }

    override fun getCount(): Int { //return total
        return 2
    }

}