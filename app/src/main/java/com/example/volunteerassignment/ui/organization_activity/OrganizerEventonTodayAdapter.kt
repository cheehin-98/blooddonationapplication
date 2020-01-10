/*
package com.example.volunteerassignment.ui.organization_activity

import android.content.Context
import android.graphics.BitmapFactory
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import com.example.volunteerassignment.R
import com.google.firebase.storage.FirebaseStorage

class RecycleViewAdapters(val context: Context, val EventTitle: ArrayList<String>, private val eventImage: ArrayList<String>, private val eventRegisterDate: ArrayList<String>) :
    RecyclerView.Adapter<RecycleViewAdapter.ImgViewHolder>() {

    val Organizer_UID = "wf7aHafXMEP9kpTQZq9QhhqCf9y2"
    val storage = FirebaseStorage.getInstance()
    val ONE_MEGABYTE = (1024 * 1024).toLong()

    override fun getItemCount(): Int {
        return eventImage.size
    }

    */
/*override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImgViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater
            .inflate(R.layout.fragment_organization_update_event_cardview, parent, false)
        return ImgViewHolder(view)

    }*//*


    override fun onBindViewHolder(holder: RecycleViewAdapter.ImgViewHolder, position: Int) {
        holder.txtPrize.text = eventRegisterDate[position]

        val rewardRef= storage.reference.child("Event/Organizer_UID/"+ Organizer_UID +"/"+ eventImage[position]+".jpg")

        rewardRef.getBytes(ONE_MEGABYTE).addOnSuccessListener { bytes ->
            val bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
            holder.prizeImg.setImageBitmap(bmp)
        }
            .addOnFailureListener {
                holder.prizeImg.setImageResource(R.drawable.ic_upload_image)
            }


        */
/*   holder.prizeImg.setOnClickListener {
               val intent =Intent(context, PrizeDetailActivity::class.java)
               intent.putExtra("prizeID",images[position])
               context.startActivity(intent)
           }*//*

    }

    class ImgViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val eventImg = view.findViewById<ImageButton>(R.id.event_image2)
        val txtEventtitle = view.findViewById<TextView>(R.id.eventTitle)
        val txtRegisterDate = view.findViewById<TextView>(R.id.registerDate)
    }


}*/
