package com.example.blooddonationapplication.ui.donorEvent

import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.blooddonationapplication.R
import com.google.firebase.storage.FirebaseStorage

class EventViewAdapter (val context: Context, val eventName: ArrayList<String>, private val eventImg: ArrayList<String>) :
    RecyclerView.Adapter<EventViewAdapter.EnventViewHolder>() {

    val storage = FirebaseStorage.getInstance()
    val ONE_MEGABYTE = (1024 * 1024).toLong()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EnventViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater
            .inflate(R.layout.event_single, parent, false)
        return EnventViewHolder(view)
    }

    override fun getItemCount(): Int {
        return eventName.size
    }

    override fun onBindViewHolder(holder: EnventViewHolder, position: Int) {


        holder.txtEvent.text = eventName[position]

        val eventRef= storage.reference.child("Event/"+eventImg[position]+".jpg")

        eventRef.getBytes(ONE_MEGABYTE).addOnSuccessListener { bytes ->
            val bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
            holder.eventImg.setImageBitmap(bmp)
            }
            .addOnFailureListener {
                holder.eventImg.setImageResource(R.drawable.ic_image_black_24dp)
            }

        holder.eventImg.setOnClickListener {
            val intent =Intent(context,eventActivityDetails::class.java)
            intent.putExtra("EventID",eventImg[position])
            intent.putExtra("Type","event")
            context.startActivity(intent)
        }
    }

    class EnventViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val eventImg = view.findViewById<ImageButton>(R.id.eventImg)
        val txtEvent = view.findViewById<TextView>(R.id.eventTxt)

    }
}