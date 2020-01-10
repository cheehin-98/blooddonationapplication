package com.example.volunteerassignment.ui.user_point_and_redeem

import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import com.example.volunteerassignment.R
import com.google.firebase.storage.FirebaseStorage

class ListAdapter (val context: Context, val rewardName: ArrayList<String>, private val point: ArrayList<String>,private val date: ArrayList<String>,private val rewardID:ArrayList<String>) :
    RecyclerView.Adapter<ListAdapter.TxtViewHolder>() {

    val storage = FirebaseStorage.getInstance()
    val ONE_MEGABYTE = (1024 * 1024).toLong()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListAdapter.TxtViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater
            .inflate(R.layout.reward_history_single, parent, false)
        return TxtViewHolder(view)
    }

    override fun getItemCount(): Int {
        return rewardName.size
    }

    override fun onBindViewHolder(holder: ListAdapter.TxtViewHolder, position: Int) {
        holder.txtDate.text = date[position]
        holder.txtPoint.text = point[position]
        holder.txtName.text = rewardName[position]

        val rewardRef= storage.reference.child("Prize/"+rewardID[position]+".jpg")

        rewardRef.getBytes(ONE_MEGABYTE).addOnSuccessListener { bytes ->
            val bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
            holder.rewardImage.setImageBitmap(bmp)
        }
            .addOnFailureListener {
                holder.rewardImage.setImageResource(R.drawable.gold_trophy)
            }

        holder.btnDetail.setOnClickListener {
            val intent = Intent(context,PrizeDetailActivity::class.java)
            intent.putExtra("prizeID",rewardID[position])
            intent.putExtra("Call", "History")
            context.startActivity(intent)
        }
    }

    class TxtViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val txtName = view.findViewById<TextView>(R.id.txtName)
        val txtPoint = view.findViewById<TextView>(R.id.txtPoint)
        val txtDate = view.findViewById<TextView>(R.id.txtDate)
        val btnDetail = view.findViewById<Button>(R.id.btnDetails)
        val rewardImage = view.findViewById<ImageView>(R.id.rewardImg)

    }
}