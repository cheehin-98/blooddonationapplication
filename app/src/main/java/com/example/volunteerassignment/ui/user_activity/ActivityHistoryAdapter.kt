package com.example.volunteerassignment.ui.user_activity

import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.volunteerassignment.R
import com.example.volunteerassignment.ui.home.eventActivityDetails
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import java.text.SimpleDateFormat

class ActivityHistoryAdapter (val context: Context, val eventID: ArrayList<String>) :
    RecyclerView.Adapter<ActivityHistoryAdapter.TxtViewHolder>() {


    private lateinit var storage: FirebaseStorage
    private lateinit var ref: FirebaseFirestore
    val ONE_MEGABYTE = (1024 * 1024).toLong()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActivityHistoryAdapter.TxtViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater
            .inflate(R.layout.my_activity_event_single, parent, false)
        return TxtViewHolder(view)
    }

    override fun getItemCount(): Int {
        return eventID.size
    }

    override fun onBindViewHolder(holder: ActivityHistoryAdapter.TxtViewHolder, position: Int) {

        ref = FirebaseFirestore.getInstance()
        storage = FirebaseStorage.getInstance()

        ref.collection("Event").document(eventID[position])
            .get()
            .addOnSuccessListener { document ->
                holder.txtName.text = document.get("Event Title").toString()
                holder.txtAddr.text = document.get("Address").toString()

                holder.txtDuration.text = document.get("From Date").toString() + "-"+document.get("To Date").toString()
            }

        val eventImgRef = storage.reference.child("Event/" + eventID[position] + ".jpg")

        eventImgRef.getBytes(ONE_MEGABYTE).addOnSuccessListener { bytes ->
            val bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
            holder.eventImage.setImageBitmap(bmp)
        }

        holder.btnDetail.setOnClickListener{
            val intent = Intent(context, eventActivityDetails::class.java)
            intent.putExtra("EventID",eventID[position])
            intent.putExtra("Type","activity")
            context.startActivity(intent)
        }
    }

    class TxtViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val txtName = view.findViewById<TextView>(R.id.txtName)
        val txtDuration = view.findViewById<TextView>(R.id.txtDuration)
        val txtAddr = view.findViewById<TextView>(R.id.txtAddr)
        val btnDetail = view.findViewById<Button>(R.id.btnDetails)
        val eventImage = view.findViewById<ImageView>(R.id.eventImg)

    }
}
