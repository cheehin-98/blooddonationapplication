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

class RecycleViewAdapter(val context: Context, val EventTitle: ArrayList<String>, private val eventImages: ArrayList<String>) :
    RecyclerView.Adapter<RecycleViewAdapter.ImgViewHolder>() {

    val Organizer_UID = "GiagDmqIQJZZOOHPHqhnbAedGLh1"
    val storage = FirebaseStorage.getInstance()
    val ONE_MEGABYTE = (1024 * 1024).toLong()

    override fun getItemCount(): Int {
        return eventImages.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImgViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater
            .inflate(R.layout.fragment_organization_update_event_cardview, parent, false)
        return ImgViewHolder(view)

    }

    override fun onBindViewHolder(holder: ImgViewHolder, position: Int) {
        holder.txtEventTitle.text = EventTitle[position]

        //find img
        val rewardRef= storage.reference.child("Event/Organizer_UID/"+ Organizer_UID +"/"+ eventImages[position]+".jpg")

        rewardRef.getBytes(ONE_MEGABYTE).addOnSuccessListener { bytes ->
            val bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
            holder.eventImg.setImageBitmap(bmp)
        }
            .addOnFailureListener {
                holder.eventImg.setImageResource(R.drawable.ic_upload_image)
            }


      holder.cardUpdateListView.setOnClickListener {
            val intent = Intent(context, OrganizerUpdateEventActivity::class.java)
            intent.putExtra("imgID",eventImages[position])
            context.startActivity(intent)
        }


    }

    class ImgViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val eventImg = view.findViewById<ImageButton>(R.id.event_image)
        val txtEventTitle = view.findViewById<TextView>(R.id.eventTitle)
        val cardUpdateListView = view.findViewById<CardView>(R.id.cardUpdateListView)


    }
}