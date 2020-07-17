/*package com.example.blooddonationapplication.ui.recycleview.item

import android.content.ClipData
import android.content.Context
import com.bumptech.glide.Glide
import com.example.blooddonationapplication.R
import com.example.blooddonationapplication.ui.model.User
import com.xwray.groupie.kotlinandroidextensions.Item
import com.xwray.groupie.kotlinandroidextensions.ViewHolder
import kotlinx.android.synthetic.main.fragment_chat_card_view.*
import java.util.*

class personItem (val person:User,
                  val userId: String,
                  private val context: Context
)
    :Item(){
    override fun bind(viewHolder: ViewHolder, position: Int) {
       viewHolder.chat_username.text = person.name
        viewHolder.chat_bio.text= person.email
        if (person.profileRef !=null)
            Glide.with(context)
                .load(storageUtil)
    }

    override fun getLayout() = R.layout.fragment_chat_card_view

    }*/



