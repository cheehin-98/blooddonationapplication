package com.example.volunteerassignment.ui.user_activity

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.volunteerassignment.R
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class ActViewAdapter (val context: Context, val eventID: ArrayList<String>) :
    RecyclerView.Adapter<ActViewAdapter.TxtViewHolder>() {


    private lateinit var storage: FirebaseStorage
    private lateinit var ref: FirebaseFirestore

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActViewAdapter.TxtViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater
            .inflate(R.layout.my_activity_single, parent, false)
        return TxtViewHolder(view)
    }

    override fun getItemCount(): Int {
        return eventID.size
    }

    override fun onBindViewHolder(holder: ActViewAdapter.TxtViewHolder, position: Int) {

        ref = FirebaseFirestore.getInstance()
        storage = FirebaseStorage.getInstance()

        ref.collection("Event").document(eventID[position])
            .get()
            .addOnSuccessListener { document ->
                //holder.txtName.text = document.get("")
                //
            }
//        holder.btnDetail.text = point[position]
//        holder.eventImage = rewardName[position]

    }

    class TxtViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val txtName = view.findViewById<TextView>(R.id.txtName)
        val txtDay = view.findViewById<TextView>(R.id.txtDay)
        val txtDuration = view.findViewById<TextView>(R.id.txtDuration)
        val txtAddr = view.findViewById<TextView>(R.id.txtAddr)
        val btnDetail = view.findViewById<Button>(R.id.btnDetails)
        val eventImage = view.findViewById<ImageView>(R.id.eventImg)

    }
}
