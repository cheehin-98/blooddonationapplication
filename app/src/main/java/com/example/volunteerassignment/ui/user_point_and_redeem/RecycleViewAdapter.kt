package com.example.volunteerassignment.ui.user_point_and_redeem

import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import com.example.volunteerassignment.R
import com.google.firebase.storage.FirebaseStorage


class RecycleViewAdapter(val context: Context, val imageName: ArrayList<String>, private val images: ArrayList<String>) :
    RecyclerView.Adapter<RecycleViewAdapter.ImgViewHolder>() {

    val storage = FirebaseStorage.getInstance()
    val ONE_MEGABYTE = (1024 * 1024).toLong()

    override fun getItemCount(): Int {
        return images.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImgViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater
            .inflate(R.layout.prize_single_view, parent, false)
        return ImgViewHolder(view)

    }

    override fun onBindViewHolder(holder: ImgViewHolder, position: Int) {
        holder.txtPrize.text = imageName[position]

        val rewardRef= storage.reference.child("Prize/"+images[position]+".jpg")

        rewardRef.getBytes(ONE_MEGABYTE).addOnSuccessListener { bytes ->
            val bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
                holder.prizeImg.setImageBitmap(bmp)
            }
            .addOnFailureListener {
                holder.prizeImg.setImageResource(R.drawable.gold_trophy)
            }


        holder.prizeImg.setOnClickListener {
            val intent =Intent(context,PrizeDetailActivity::class.java)
            intent.putExtra("prizeID",images[position])
            context.startActivity(intent)
        }
    }


    class ImgViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val prizeImg = view.findViewById<ImageButton>(R.id.prizeImg)
        val txtPrize = view.findViewById<TextView>(R.id.prizetxt)

    }
}
