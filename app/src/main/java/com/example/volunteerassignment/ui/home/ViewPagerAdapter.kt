package com.example.volunteerassignment.ui.home

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import com.example.volunteerassignment.R
import kotlinx.android.synthetic.main.fragment_home.view.*

class ViewPagerAdapter (private val context: Context): PagerAdapter() {
    private var layoutInflater: LayoutInflater? = null
    private val images = arrayOf(
        R.drawable.blood_donation_event,
        R.drawable.mns_volunteer,
        R.drawable.run_event
    )

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object`
    }

    override fun getCount(): Int {
        return images.size
    }

   // override fun instantiateItem(container: ViewGroup, position: Int): Any {
      //  layoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
       // val v = layoutInflater!!.inflate(R.layout.fragment_home, null)
      //  return super.instantiateItem(container, position)
        //val image = v.findViewById<View>(R.id.viewEvents as ImageView
        //image.setImageResource(images[position])

      //  val vp = container  as ViewPager
        //vp.addView(v, 0)
        //return v
    }

  //  override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
   //     val vp = container as ViewPager
    //    val v = `object`as View
    //    vp.removeView(v)
  //  }
//}

