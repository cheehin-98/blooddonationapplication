package com.example.volunteerassignment.ui.home

import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.example.volunteerassignment.R
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage

class eventActivityDetails : AppCompatActivity() {


    private lateinit var storage: FirebaseStorage
    private lateinit var ref: FirebaseFirestore

    private lateinit var eventTitle: TextView
    private lateinit var evenID:TextView
    private lateinit var detaildate:TextView
    private lateinit var detailtime:TextView
    private lateinit var detailAddress:TextView
    private lateinit var venue:TextView
    private lateinit var requiredPoint:TextView
    private lateinit var description:TextView
    private lateinit var imageevent: ImageView
    private lateinit var btnRegis:Button



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.events_detail_register)

        ref = FirebaseFirestore.getInstance()
        storage = FirebaseStorage.getInstance()

        val extras = this.intent.extras
        val eventID = extras!!.getString("EventID")


        eventTitle= findViewById(R.id.event_title)
        evenID= findViewById(R.id.id_number)

        detaildate=findViewById(R.id.detail_date)
        detailtime=findViewById(R.id.detail_time)
        detailAddress=findViewById(R.id.detail_Address)
        venue=findViewById(R.id.name_venue)
        requiredPoint=findViewById(R.id.no_of_point)
        description=findViewById(R.id.detail_description)

        imageevent=findViewById(R.id.image_event)

        val ONE_MEGABYTE = (1024 * 1024).toLong()

        ref.document("Event/" + eventID.toString())
            .get()
            .addOnSuccessListener { documentSnapshot ->
                if (documentSnapshot.exists()) {
                    eventTitle.text =(documentSnapshot.get("Event Title").toString())
                    evenID.text = documentSnapshot.id.toString()
                    detaildate.text = documentSnapshot.get("From Date").toString()+"-"+documentSnapshot.get("To Date").toString()
                    detailtime.text=documentSnapshot.get("From Time").toString()+"-"+documentSnapshot.get("To Time").toString()
                    detailAddress.text=documentSnapshot.get("Address").toString()
                    venue.text= documentSnapshot.get("Venue").toString()
                    requiredPoint.text=documentSnapshot.get("Point").toString()
                    description.text=documentSnapshot.get("Description").toString()

                    } else {
                    Toast.makeText(this, "Unable to retrieve data!", Toast.LENGTH_SHORT).show()
                }
                val eventStorageRef = storage.reference.child("Event/"+eventID.toString() +".jpg")
                eventStorageRef.getBytes(ONE_MEGABYTE).addOnSuccessListener { bytes ->
                    val bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
                    imageevent.setImageBitmap(bmp)
                    }.addOnFailureListener {
                        imageevent.setImageResource(R.drawable.ic_menu_camera)
                    }
            }
            .addOnFailureListener { exception ->
                Toast.makeText(this, "Unable to retrieve data!", Toast.LENGTH_SHORT).show()
            }
    }
}
