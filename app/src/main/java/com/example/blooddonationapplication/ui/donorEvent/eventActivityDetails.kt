package com.example.blooddonationapplication.ui.donorEvent

import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import com.example.blooddonationapplication.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage

class eventActivityDetails : AppCompatActivity() {


    private lateinit var storage: FirebaseStorage
    private lateinit var ref: FirebaseFirestore
    private lateinit var mAuth: FirebaseAuth

    private lateinit var eventTitle: TextView
    private lateinit var evenID:TextView
    private lateinit var detaildate:TextView
    private lateinit var detailtime:TextView
    private lateinit var detailAddress:TextView
    private lateinit var venue:TextView
    private lateinit var description:TextView
    private lateinit var imageevent: ImageView
    private lateinit var btnRegis:Button




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.events_detail_register)

        ref = FirebaseFirestore.getInstance()
        storage = FirebaseStorage.getInstance()
        mAuth = FirebaseAuth.getInstance()


        val extras = this.intent.extras
        val eventID = extras!!.getString("EventID")

        val type = extras.getString("Type")


        eventTitle= findViewById(R.id.event_title)
        evenID= findViewById(R.id.id_number)
        detaildate=findViewById(R.id.detail_date)
        detailtime=findViewById(R.id.detail_time)
        detailAddress=findViewById(R.id.detail_Address)
        venue=findViewById(R.id.name_venue)
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



        btnRegis = findViewById(R.id.btn_Regis)
        if(type=="event") {
            btnRegis.setOnClickListener {
                val currentuser = mAuth.currentUser

                if (currentuser != null) {

                    ref.collection("Event_Go").whereEqualTo("Event_ID", eventID)
                        .whereEqualTo("UID", currentuser?.uid)
                        .get()
                        .addOnSuccessListener { documentSnapshot ->
                            if (documentSnapshot.isEmpty) {
                                val eventGo = hashMapOf(
                                    "Event_ID" to eventID,
                                    "Sign_In" to "No",
                                    "UID" to currentuser?.uid
                                )
                                ref.collection("Event_Go")
                                    .add(eventGo)
                            }
                            this.finish()
                        }
                }
            }
        }else if (type=="activity") {
            btnRegis.visibility = View.GONE
        }
    }
}
