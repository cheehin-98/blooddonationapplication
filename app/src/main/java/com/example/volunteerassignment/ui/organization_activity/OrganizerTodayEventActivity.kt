package com.example.volunteerassignment.ui.organization_activity

import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import com.example.volunteerassignment.R
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import java.util.*

class OrganizerTodayEventActivity : AppCompatActivity() {

   // private lateinit var btnGenAttendance: Button
    private lateinit var fromDate : TextView
    private lateinit var toDate : TextView
    private lateinit var fromTime : TextView
    private lateinit var toTime : TextView
    private lateinit var eventTitle : TextView
    private lateinit var numOfParti : TextView
    private lateinit var point : TextView
    private lateinit var address : TextView
    private lateinit var venue : TextView
    private lateinit var description : TextView
    private lateinit var eventImage: ImageView
    private lateinit var btnGenAttendance: Button
    private lateinit var imgID: String
    private lateinit var storage: FirebaseStorage
    private lateinit var database: FirebaseFirestore

    val Organizer_UID = "GiagDmqIQJZZOOHPHqhnbAedGLh1"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_organizer_today_event)

        imgID = intent.getStringExtra("imgID")


       btnGenAttendance.findViewById<View>(R.id.btnGenerateAttendance)

        btnGenAttendance.setOnClickListener {
            val intent = Intent(this, OrganizerGenerateAttedance::class.java)
            intent.putExtra("imgID", imgID)
            this.startActivity(intent)
        }

    }

    override fun onStart() {
        super.onStart()
        fromDate = findViewById(R.id.editEventFromDatet)
        toDate = findViewById(R.id.editEventToDatet)
        fromTime = findViewById(R.id.editEventFromTimet)
        toTime = findViewById(R.id.editEventToTimet)
        eventTitle = findViewById(R.id.editEventTitlet)
        numOfParti = findViewById(R.id.editEventNumOfParticipatet)
        point = findViewById(R.id.editEventPointt)
        address = findViewById(R.id.editEventAddresst)
        venue = findViewById(R.id.editEventVenuet)
        description = findViewById(R.id.editEventDescriptiont)
        eventImage = findViewById(R.id.event_imaget)


        storage = FirebaseStorage.getInstance()
        database = FirebaseFirestore.getInstance()
        val ONE_MEGABYTE = (1024 * 1024).toLong()
        val UserRef = database.collection("Event").document(imgID)
        val rewardRef= storage.reference.child("Event/"+ imgID+".jpg")


        rewardRef.getBytes(ONE_MEGABYTE).addOnSuccessListener { bytes ->
            val bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
            eventImage.setImageBitmap(bmp)
        }
            .addOnFailureListener {
                eventImage.setImageResource(R.drawable.ic_menu_camera)
            }


        UserRef.addSnapshotListener{ snapshot, e ->
            if(snapshot!!.exists()){
                fromDate.append(snapshot.get("From Date").toString())
                toDate.append(snapshot.get("To Date").toString())
                fromTime.append(snapshot.get("From Time").toString())
                toTime.append(snapshot.get("To Time").toString())
                eventTitle.append(snapshot.get("Event Title").toString())
                numOfParti.append(snapshot.get("Number of Participate").toString())
                point.append(snapshot.get("Point").toString())
                address.append(snapshot.get("Address").toString())
                venue.append(snapshot.get("Venue").toString())
                description.append(snapshot.get("Description").toString())

            }

            else{
                Toast.makeText(this, "Unable to retrieve data!", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
