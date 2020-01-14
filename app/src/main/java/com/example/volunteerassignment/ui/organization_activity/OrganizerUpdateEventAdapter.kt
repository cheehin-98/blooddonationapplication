package com.example.volunteerassignment.ui.organization_activity

import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.cardview.widget.CardView
import com.example.volunteerassignment.R
import com.google.firebase.storage.FirebaseStorage

class RecycleViewAdapter(val context: Context, val eventTitle: ArrayList<String>, private val eventImage: ArrayList<String>, private val eventFromDate: ArrayList<String>, private val eventToDate: ArrayList<String>) :
    RecyclerView.Adapter<RecycleViewAdapter.ImgViewHolder>() {

    val Organizer_UID = "kfRxZs5pAvdiQk8F67ZfrjgaA9q1"
    val storage = FirebaseStorage.getInstance()
    val ONE_MEGABYTE = (1024 * 1024).toLong()

    override fun getItemCount(): Int {
        return eventImage.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImgViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater
            .inflate(R.layout.fragment_organization_update_event_cardview, parent, false)
        return ImgViewHolder(view)

    }

    override fun onBindViewHolder(holder: ImgViewHolder, position: Int) {
        holder.txtEventTitle.text = eventTitle[position]
        holder.txtEventFromDate.text = "From Date: " + eventFromDate[position]
        holder.txtEventToDate.text = "To Date: " + eventToDate[position]

        //find img
        val rewardRef= storage.reference.child("Event/"+ eventImage[position]+".jpg")

        rewardRef.getBytes(ONE_MEGABYTE).addOnSuccessListener { bytes ->
            val bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
            holder.eventImg.setImageBitmap(bmp)
        }
            .addOnFailureListener {
                holder.eventImg.setImageResource(R.drawable.ic_upload_image)
            }


      holder.cardUpdateListView.setOnClickListener {
            val intent = Intent(context, OrganizerUpdateEventActivity::class.java)
            intent.putExtra("imgID",eventImage[position])
            context.startActivity(intent)
        }
    }

    class ImgViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val eventImg = view.findViewById<ImageButton>(R.id.event_imageu)
        val txtEventTitle = view.findViewById<TextView>(R.id.eventTitleu)
        val txtEventFromDate = view.findViewById<TextView>(R.id.eventFromDateu)
        val txtEventToDate = view.findViewById<TextView>(R.id.eventToDateu)
        val cardUpdateListView = view.findViewById<CardView>(R.id.cardUpdateListView)


    }
}